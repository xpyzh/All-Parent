/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * @author emeroad
 */
public final class NetworkUtils {

    public static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    public static final String ERROR_HOST_NAME = "UNKNOWN-HOST";

    private static final String LOOPBACK_ADDRESS_V4 = "127.0.0.1";


    private static final String LOOPBACK_ADDRESS_V6 = "0:0:0:0:0:0:0:1";

    private static final List<String> LOOP_BACK_ADDRESS_LIST;


    static {
        LOOP_BACK_ADDRESS_LIST = new ArrayList<>(3);
        LOOP_BACK_ADDRESS_LIST.add(LOOPBACK_ADDRESS_V4);
        LOOP_BACK_ADDRESS_LIST.add(LOOPBACK_ADDRESS_V6);
    }

    //获取机器的hostName,这里兼容做了机器的hostName没有做dns解析的情况
    public static String getHostName() {
        String hostName = StringUtils.EMPTY;
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            hostName = localHost.getHostName();
        } catch (UnknownHostException e) {
            //执行linux的hostname命令直接获取
            hostName = getHostNameCompensate();
            if (StringUtils.isBlank(hostName)) {
                // Try to get machine name from network interface.
                hostName = getMachineName();
            }
        }
        logger.info("[op:getHostName] hostName={}", hostName);
        return hostName;
    }

    public static String getRepresentationHostIp() {
        String hostIp = getHostIp();
        //过滤掉本地回环地址
        if (isLoopbackAddress(hostIp)) {
            List<String> ipList = getHostIpList();
            if (ipList != null && ipList.size() > 0) {
                boolean isFind = false;
                //找到第一个不是ipv6的地址
                for (String ip : ipList) {
                    if (StringUtils.isNotBlank(ip) && validationIpV4FormatAddress(ip)) {
                        hostIp = ip;
                        isFind = true;
                        break;
                    }
                }
                //实在找不到就用127.0.0.1
                if (!isFind) {
                    hostIp = LOOPBACK_ADDRESS_V4;
                }
            }
        }
        logger.info("[op:getRepresentationHostIp] hostIp={}", hostIp);
        return hostIp;
    }

    public static String getHostV4Ip() {
        String hostIp = getHostIp();
        if (validationIpV4FormatAddress(hostIp)) {
            return hostIp;
        }
        return LOOPBACK_ADDRESS_V4;
    }

    public static List<String> getHostV4IpList() {
        List<String> hostIpList = getHostIpList();
        List<String> hostV4IpList = new ArrayList<String>(hostIpList.size());
        for (String ip : hostIpList) {
            if (validationIpV4FormatAddress(ip)) {
                hostV4IpList.add(ip);
            }
        }
        return hostV4IpList;
    }

    private static String getHostIp() {
        String hostIp;
        try {
            final InetAddress thisIp = InetAddress.getLocalHost();
            hostIp = thisIp.getHostAddress();
        } catch (UnknownHostException e) {
            hostIp = LOOPBACK_ADDRESS_V4;
        }
        return hostIp;
    }

    private static List<String> getHostIpList() {
        List<String> result = new ArrayList<String>();
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ignore) {
            // skip
        }
        if (interfaces == null) {
            return Collections.EMPTY_LIST;
        }
        while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            if (isSkipNetworkInterface(current)) {
                continue;
            }
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address.isLoopbackAddress()) {
                    continue;
                }

                String hostAddress = address.getHostAddress();
                if (!isLoopbackAddress(hostAddress)) {
                    result.add(address.getHostAddress());
                }
            }
        }

        return result;
    }

    private static boolean isSkipNetworkInterface(NetworkInterface networkInterface) {
        try {
            if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
                return true;
            }
            return false;
        } catch (Exception ignore) {
            // skip
        }
        return true;
    }

    //判断是否是本地回环地址
    private static boolean isLoopbackAddress(String ip) {
        if (ip == null) {
            return true;
        }
        return LOOP_BACK_ADDRESS_LIST.contains(ip);
    }

    private static boolean validationIpV4FormatAddress(String address) {
        try {
            String[] eachDotAddress = address.split("\\.");
            if (eachDotAddress.length != 4) {
                return false;
            }

            for (String eachAddress : eachDotAddress) {
                if (Integer.parseInt(eachAddress) > 255) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException ignore) {
            // skip
        }
        return false;
    }

    @Deprecated
    private static String getMachineName() {
        try {
            Enumeration<NetworkInterface> enet = NetworkInterface.getNetworkInterfaces();
            while (enet.hasMoreElements()) {

                NetworkInterface net = enet.nextElement();
                if (net.isLoopback()) {
                    continue;
                }

                Enumeration<InetAddress> eaddr = net.getInetAddresses();

                while (eaddr.hasMoreElements()) {
                    InetAddress inet = eaddr.nextElement();

                    final String canonicalHostName = inet.getCanonicalHostName();
                    if (!canonicalHostName.equalsIgnoreCase(inet.getHostAddress())) {
                        return canonicalHostName;
                    }
                }
            }
            return ERROR_HOST_NAME;
        } catch (SocketException e) {
            logger.error("[op:getMachineName]", e.getMessage(), e);
            return ERROR_HOST_NAME;
        }
    }

    public static String getHostFromURL(final String urlSpec) {
        if (urlSpec == null) {
            return null;
        }
        try {
            final URL url = new URL(urlSpec);

            final String host = url.getHost();
            final int port = url.getPort();

            if (port == -1) {
                return host;
            } else {
                // TODO should we still specify the port number if default port is used?
                return host + ":" + port;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }

    //直接使用cmd命令获取机器hostName
    private static String getHostNameCompensate() {
        String hostName = System.getenv("HOSTNAME");
        if (StringUtils.isNotBlank(hostName)) {
            return hostName;
        }
        String execCommand = "hostname";
        try {
            Scanner s = new Scanner(Runtime.getRuntime().exec(execCommand).getInputStream()).useDelimiter("\\A");
            hostName = s.hasNext() ? s.next().trim() : null;
        } catch (IOException e) {
            //ignore
        }
        return hostName;
    }

    //test
    public static void main(String[] args) {
        System.out.println("getHostName():" + getHostName());
        System.out.println("getHostNameCompensate():" + getHostNameCompensate());
        System.out.println("getMachineName():" + getMachineName());
        System.out.println("getRepresentationHostIp():" + getRepresentationHostIp());
        System.out.println("getHostIp():" + getHostIp());
        System.out.println("getHostIpList():" + getHostIpList());
        System.out.println("getHostV4Ip():" + getHostV4Ip());
        System.out.println("getHostV4IpList():" + getHostV4IpList());
    }
}

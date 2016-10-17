package model;

/**
 * Created by youzhihao on 2016/10/14.
 * 免费代理的model
 */
public class FreeProxy {

    private String ip;

    private int port;

    public FreeProxy(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeProxy freeProxy = (FreeProxy) o;

        if (port != freeProxy.port) return false;
        return ip.equals(freeProxy.ip);

    }

    @Override
    public int hashCode() {
        int result = ip.hashCode();
        result = 31 * result + port;
        return result;
    }
}

package udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.commons.lang.StringUtils;

import java.io.RandomAccessFile;

/**
 * Created by youzhihao on 2017/7/27.
 */
public class LogEventBroadcaster {

    public void run() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioDatagramChannel.class);
            b.option(ChannelOption.SO_BROADCAST, true);
            b.handler(new ChannelInitializer<DatagramChannel>() {
                @Override
                protected void initChannel(DatagramChannel ch) throws Exception {
                    ch.pipeline().addLast(new LogEventEncoder());
                }
            });
            //因为是发送端，所以随便绑定一个端口和ip，这部分只是采集日志并发送udp报文信息
            Channel ch = b.bind(0).sync().channel();
            long pointer = 0;
            for (; ; ) {
                String logPath = LogEventBroadcaster.class.getResource("/log.txt").getFile();
                RandomAccessFile file = new RandomAccessFile(logPath, "r");
                file.seek(pointer);
                String line = StringUtils.EMPTY;
                while ((line = file.readLine()) != null) {
                    int index = line.indexOf("[");
                    if (index > 0) {
                        String time = line.substring(0, index);
                        String message = line.substring(index);
                        ch.writeAndFlush(new LogEvent("127.0.0.1", 9999, time, message));
                    }
                }
                pointer = file.getFilePointer();
                file.close();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    break;
                }
            }
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        new LogEventBroadcaster().run();
    }

}

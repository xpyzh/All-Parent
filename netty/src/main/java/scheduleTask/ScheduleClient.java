package scheduleTask;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by youzhihao on 2016/9/26.
 */
public class ScheduleClient {

    private String host = "127.0.0.1";

    private int port = 8080;

    public void start() throws Exception {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(loopGroup);
            b.channel(NioSocketChannel.class);
            b.remoteAddress(new InetSocketAddress(host, port));
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ScheduleClientHandler());
                }
            });
            ChannelFuture channelFuture = b.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new ScheduleClient().start();
    }

}

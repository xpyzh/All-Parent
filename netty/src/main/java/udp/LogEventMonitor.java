package udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by youzhihao on 2017/7/27.
 */
public class LogEventMonitor {

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioDatagramChannel.class);
            b.option(ChannelOption.SO_BROADCAST, true);
            b.handler(new ChannelInitializer<DatagramChannel>() {
                @Override
                protected void initChannel(DatagramChannel ch) throws Exception {
                    ch.pipeline().addLast(new LogEventDecoder());
                }
            });
            ChannelFuture future = b.bind(9999).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws Exception {
        new LogEventMonitor().start();
    }

}

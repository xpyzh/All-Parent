package scheduleTask;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2016/9/25.
 *
 * 这里使用简单的slf4j实现,开启debug模式:
 * -Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG
 * 开启netty的ResourceLeakDetector的PARANOID模式:
 * -Dio.netty.leakDetection.level=PARANOID
 */
public class ScheduleServer {

    int port = 8080;

    public void start() throws Exception {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(loopGroup);
            b.localAddress(new InetSocketAddress(port));
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //注册一个定时任务,不会随着该channel停止而停止,因为是绑定在eventloop上
                    ch.eventLoop().scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("可以检查某个连接是否正常");
                        }
                    },0L,1L, TimeUnit.SECONDS);
                    ch.pipeline().addLast(new ScheduleServerHandler());
                }
            });
            ChannelFuture channelFuture = b.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new ScheduleServer().start();
    }

}

package demo3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by hzyouzhihao on 2016/9/12.
 */
public class StrServer {

    private int port = 8080;

    public StrServer() {
    }

    public StrServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrapServer = new ServerBootstrap();
            bootstrapServer.group(boos, work);
            bootstrapServer.channel(NioServerSocketChannel.class);
            bootstrapServer.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageDecoder(), new ServerHandler());
                    //手动接受新注册连接的内容读取
                    ch.read();
                }
            });
            bootstrapServer.option(ChannelOption.SO_BACKLOG, 128);
            bootstrapServer.childOption(ChannelOption.SO_KEEPALIVE, true);
            //设置非自动读取内容
            //bootstrapServer.childOption(ChannelOption.AUTO_READ, false);
            ChannelFuture f = bootstrapServer.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
            boos.terminationFuture().sync();
            work.terminationFuture().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new StrServer().run();
    }
}

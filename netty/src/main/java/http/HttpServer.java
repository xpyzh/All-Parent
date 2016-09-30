package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * Created by youzhihao on 2016/9/30.
 */
public class HttpServer {

    int port = 8080;

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group);
            b.localAddress(new InetSocketAddress(port));
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //netty现成的http-request解码器
                    ch.pipeline().addLast("decoder", new HttpRequestDecoder());
                    //netty现成的http-response编码器
                    ch.pipeline().addLast("encoder", new HttpResponseEncoder());
                    //见HttpObjectAggregator源码注解,合并一个完整的http-request或者http-response请求
                    ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
                    ch.pipeline().addLast("custom-handler", new HttpServerHandler());
                }
            });
            ChannelFuture channelFuture = b.bind().sync();
            channelFuture.channel().closeFuture().sync();


        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        new HttpServer().start();
    }

}

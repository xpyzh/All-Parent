package http1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by youzhihao on 2017/7/25.
 */
public class HttpServer {

    int port = 8080;

    public void start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(eventLoopGroup);
            b.localAddress(port);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast(new HttpServerCodec());
                    pipeline.addLast(new HttpObjectAggregator(512 * 1024));
                    //如果不用zero-copy的FileRegion，则需要加ChunkedWriteHandler，来减少内存的消耗,避免OutOfMemoryError
                    pipeline.addLast(new ChunkedWriteHandler());
                    pipeline.addLast(new WriteStreamHandler());
                }
            });
            ChannelFuture future = b.bind().sync();
            future.channel().closeFuture().sync();

        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().start();
    }
}

package http1;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.net.URLEncoder;

/**
 * Created by youzhihao on 2017/7/25.
 */
public class WriteStreamHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        File file = new File(WriteStreamHandler.class.getResource("/demoFile.txt").getFile());
        //封装response对象
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpUtil.setContentLength(response, file.length());
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream;charset=utf-8");
        response.headers().set("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        if (HttpUtil.isKeepAlive((HttpMessage) msg)) {
            response.headers().set("CONNECTION", HttpHeaderNames.CONNECTION);
        }
        ctx.write(response);
        //使用zero-copy传输，减少将文件放入jvm内存这一步，直接复制到外部内存
//        FileInputStream in = new FileInputStream(file);
//        FileRegion region = new DefaultFileRegion(in.getChannel(), 0, file.length());
//        ctx.write(region);
        //使用普通的传输
        ChunkedFile chunkedFile = new ChunkedFile(file);
        ctx.write(chunkedFile);
        ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
}

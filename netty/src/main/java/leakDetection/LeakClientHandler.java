package leakDetection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by youzhihao on 2016/9/26.
 */
public class LeakClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接上服务器,则不断发消息给服务器
        for (int i = 0; i < 10000000; i++) {
            ByteBuf buf=ctx.alloc().buffer();
            buf.writeCharSequence("hello netty",CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
        }
    }
}

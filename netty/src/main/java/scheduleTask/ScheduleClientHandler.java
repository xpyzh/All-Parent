package scheduleTask;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by youzhihao on 2016/9/26.
 */
public class ScheduleClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接上服务器,则不断发消息给服务器
        for (int i = 0; i < 1; i++) {
            ByteBuf buf=ctx.alloc().buffer();
            buf.writeCharSequence("hello netty",CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
        }
    }
}

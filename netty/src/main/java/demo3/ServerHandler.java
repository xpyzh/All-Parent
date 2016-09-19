package demo3;

import com.google.common.base.Strings;
import com.sun.tools.javac.util.StringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by hzyouzhihao on 2016/9/12.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Message message =(Message)msg;
            //可以给固定的channel附加一些对象,这些对象属于该channel
            AttributeKey<String> attrKey= AttributeKey.valueOf("aa");
            Attribute<String> attrValue=ctx.channel().attr(attrKey);
            System.out.println("attrValue="+attrValue.getAndSet(message.getContent()));
            // channel注册在某一个EventLoop上,EventLoop是由EventLoopGroup管理的,EventLoop继承了ScheduledExecutorService
            // 可以调用该channel所注册的excutor,执行一些异步任务,定时任务
            ctx.executor().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("异步任务完成");
                }
            });
            System.out.println(message.getContent());
        }
        finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

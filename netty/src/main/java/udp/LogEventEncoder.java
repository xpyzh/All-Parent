package udp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by youzhihao on 2017/7/26.
 * LogEvent对象的编码器
 * LogEvent---->DatagramPacket
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }
        InetSocketAddress address = new InetSocketAddress(msg.getIp(), msg.getPort());
        byte[] bytes = JSONObject.toJSONBytes(msg, SerializerFeature.PrettyFormat);
        ByteBuf byteBuf = ctx.alloc().buffer(bytes.length);
        byteBuf.writeBytes(bytes);
        out.add(new DatagramPacket(byteBuf, address));
    }
}

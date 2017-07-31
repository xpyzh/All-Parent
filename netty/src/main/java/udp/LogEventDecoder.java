package udp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by youzhihao on 2017/7/27.
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf directBuf = msg.content();
        int length = directBuf.readableBytes();
        byte[] array = new byte[length];
        directBuf.getBytes(0,array);
        LogEvent logEvent = JSONObject.parseObject(array, LogEvent.class, Feature.InitStringFieldAsEmpty);
        System.out.println(JSONObject.toJSONString(logEvent));
    }
}

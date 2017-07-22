package embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by youzhihao on 2017/7/22.
 * 处理异常和非法请求的示例，如果超过3个可读字符，则抛弃请求内容，抛出TooLongFrameException
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {

    private int MAX_READBLE_BYTES = 3;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        if (length > MAX_READBLE_BYTES) {
            in.clear();//丢弃
            throw new TooLongFrameException();
        }
        ByteBuf buf = in.readBytes(length);
        out.add(buf);
    }
}

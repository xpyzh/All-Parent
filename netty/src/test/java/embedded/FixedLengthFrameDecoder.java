package embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by youzhihao on 2016/9/29.
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private int fixLength;

    public FixedLengthFrameDecoder(int fixLength) {
        this.fixLength = fixLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= fixLength) {
            ByteBuf buf = in.readBytes(fixLength);
            out.add(buf);
        }

    }

}

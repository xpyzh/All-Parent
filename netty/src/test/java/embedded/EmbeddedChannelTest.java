package embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by youzhihao on 2016/9/29.
 */
public class EmbeddedChannelTest {

    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        //这里为什么要复制一份?单独获得一份读写索引,在下面的channel.writeInbound,会读取buf中的数据,使readIndex增加,
        //如果不复制一份,下面的断言读取数据的时候使用同一个readIndex,会出现读取异常
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3));
        //channel.writeInbound()会返回boolean型,如果是true则说明channel.readInbound能够读取到数据,也就是数据成功传输到pipline中inbound最后
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());
        //读取一份通过pipline中所有handler处理后获取的数据
        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = channel.readInbound();
        //这里为什么可以直接用assertEquals比较两个object,是因为byteBuf已经重写了equals,内部实现并不是比较两个对象引用是否相等,而是比较内部的内容是否相同
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        assertNull(channel.readInbound());
        buf.release();

    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3));
        //这里为什么断言false?因为默认解码器只有接收到3个字节才解码,所以读取两个,不会解码而是等待后续读取,因此channel.readInbound()读取不到数据,返回false
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));
        assertTrue(channel.finish());
        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testAbsIntegerEncoder() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        for (int i = 0; i < 10; i++) {
            channel.writeAndFlush(i);
            ByteBuf buf = channel.readOutbound();
            System.out.println(buf.readInt());
        }
    }
}

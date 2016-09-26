package bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.text.Format;
import java.text.MessageFormat;

/**
 * Created by hzyouzhihao on 2016/9/24.
 * 三种常见的ByteBuf模型
 */
public class ThreePattern {

    public static void main(String[] args) {

        // heapBuffers();
        //directBuffers();
        compositeBuffers();

    }
    /**数据储存在jvm的堆空间，当缓存池没有使用的时候,提供非常快速的分配和解除分配*/
    public static void heapBuffers() {
        ByteBuf heapBuf = Unpooled.buffer();
        heapBuf.writeBytes(new String("hello netty").getBytes(CharsetUtil.UTF_8));
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            System.out.println(MessageFormat.format("offset:{0},length:{1},size:{2},content:{3}", offset, length, array.
                    length, new String(array, offset, length, CharsetUtil.UTF_8)));
        }
    }
    /**数据直接存储在内存中，好处在I/O传输过程中，不需要将堆内数据复制到内存中，也就是少一步复制操作
     * 缺点：1.分配和释放比在堆内存中性能差
     *       2.legacy code需要在堆上重新复制一次，不懂
     * */

    public static void directBuffers() {
        ByteBuf directBuf = Unpooled.directBuffer();
        directBuf.writeBytes(new String("hello netty").getBytes(CharsetUtil.UTF_8));
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            System.out.println(MessageFormat.format("content:{0}", new String(array, CharsetUtil.UTF_8)));
        }
    }
    /**可以符合的ByteBuf,可以组合多个ByteBuf到一个CompositeByteBuf,这些ByteBuf可以是heapBuffers或者是directBuffers*/
    public static void compositeBuffers() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf directBuf = Unpooled.directBuffer();
        ByteBuf heapbuf = Unpooled.buffer();
        directBuf.writeBytes(new String("hello ").getBytes(CharsetUtil.UTF_8));
        heapbuf.writeBytes(new String("netty ").getBytes(CharsetUtil.UTF_8));
        compositeByteBuf.addComponents(directBuf, heapbuf);
        for (ByteBuf byteBuf : compositeByteBuf) {
            //组合byteBuf不支持直接访问buf
            //System.out.println(new String(byteBuf.array(), CharsetUtil.UTF_8));
            System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
        }
    }

}

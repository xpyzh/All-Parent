package fastjson.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;

/**
 * Created by youzhihao on 2018/3/5.
 */
public class FastJsonSerialization{

    public byte[] serialize(Object obj) {
        try {
            SerializeWriter out = new SerializeWriter();
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.WriteEnumUsingToString, true);
            serializer.config(SerializerFeature.WriteClassName, true);
            serializer.write(obj);
            return out.toBytes(IOUtils.UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            return JSON.parseObject(new String(bytes), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

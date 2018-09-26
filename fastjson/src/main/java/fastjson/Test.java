package fastjson;

import com.alibaba.fastjson.parser.ParserConfig;
import fastjson.model.InvocationContext;
import fastjson.model.PlainPackageBean;
import fastjson.serialize.FastJsonSerialization;

/**
 * Created by youzhihao on 2018/9/20.
 */
public class Test {
    public static void main(String[] args)
    {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        FastJsonSerialization serialization=new FastJsonSerialization();
        InvocationContext invocationContext=new InvocationContext();
        PlainPackageBean plainPackageBean=new PlainPackageBean();
        plainPackageBean.setBuyerAccount("setBuyerAccount");
        plainPackageBean.setChannelName("setChannelName");
        plainPackageBean.setDispatched(true);
        plainPackageBean.setExpCreateTime(100L);
        Object[] objArrays=new Object[1];
        objArrays[0]=plainPackageBean;
        invocationContext.setArgs(objArrays);
        byte[] content=serialization.serialize(invocationContext);
        invocationContext=serialization.deserialize(content,InvocationContext.class);
        System.out.println(invocationContext);
    }

}

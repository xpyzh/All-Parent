package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by hzyouzhihao on 2016/9/22.
 */
public class Demo1 {

    private boolean isExist; //不要使用is开头来命名本地

    //boolean类型的变量的get方法不要使用is开头
    private boolean flag;

    private String abc;

    public boolean isExist() {
        return isExist;
    }

    public void setIsExist(boolean isExist) {
        this.isExist = isExist;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void getNullException() {
       System.out.println(123);
    }

    public static void main(String[] args) {
//        Demo1 demo1 = new Demo1();
//        demo1.setIsExist(true);
//        demo1.setFlag(true);
//        String jsonStr1 = JSON.toJSONString(demo1);
//        String jsonStr2 = JSONObject.toJSONString(demo1);
//        System.out.println(jsonStr1);
//        System.out.println(jsonStr2);
        EmailMessageBean emailMessageBean=new EmailMessageBean();
        System.out.println(JSON.toJSONString(emailMessageBean));

    }

}

package fastjson;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by hzyouzhihao on 2016/9/22.
 * 结论,使用fastjson进行object转json字符串的时候,如果有isXXX,getXXX且非void返回值方法默认都会调用
 * 因此实体对象,不要在里面加任何is,get的非getter,setter方法
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

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public ArrayList getNullException() {
        return new ArrayList<>();
    }

    public String isTest() {
        return "isTest";
    }

    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        demo1.setIsExist(true);
        demo1.setFlag(true);
        System.out.println(JSONObject.toJSONString(demo1));
    }

}

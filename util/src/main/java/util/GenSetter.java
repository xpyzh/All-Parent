package util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by hzsunguanjun on 2017/3/10.
 */
public class GenSetter {
    public static void main(String[] args){
        String varName = "settleVo";
        String str = "    //供应商外部仓订单表id\n" +
                "    private Long externalOrderId;\n" +
                "\n" +
                "    //订单号（不唯一）\n" +
                "    private String orderNo;\n" +
                "\n" +
                "    //商品名称\n" +
                "    private String itemName;\n" +
                "\n" +
                "    //商品规格描述\n" +
                "    private String itemSpec;\n" +
                "\n" +
                "    //商品数量\n" +
                "    private Integer itemCount;\n" +
                "\n" +
                "    //SKUID\n" +
                "    private Long skuId;";

        String[] strArray = str.split("\n");
        String note = "";
        String[] strArray2;
        for (String tmp : strArray){
            if (tmp.contains("//") && tmp.contains(";")){
                strArray2 = tmp.trim().split("//");
                note = strArray2[1];
                strArray2 = tmp.trim().split(" ");
                System.out.println(varName + ".set" + StringUtils.capitalize(strArray2[2].substring(0, strArray2[2].length()-1)) + "(); //" + note);
            }
            else if (tmp.contains("//")){
                note = tmp.trim().substring(2);
            }
            else if (tmp.contains(";")){
                strArray2 = tmp.trim().split(" ");
                System.out.println(varName + ".set" + StringUtils.capitalize(strArray2[2].substring(0, strArray2[2].length()-1)) + "(); //" + note);
            }
        }
    }
}

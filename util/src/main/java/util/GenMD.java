package util;

/**
 * Created by hzsunguanjun on 2017/3/8.
 */
public class GenMD {
    public static void main(String[] args){
        String str = "\n" +
                "    //供应商名称\n" +
                "    private String supplierName;\n" +
                "\n" +
                "    //当前主合同编号\n" +
                "    private String contractNo;\n" +
                "\n" +
                "    //当前合同生效标记\n" +
                "    private Integer activeFlag;\n" +
                "\n" +
                "    //冲突主合同编号\n" +
                "    private List<String> conflictNoList;\n";


        System.out.println("# 库内产品巡检信息\n" +
            "|| 参数 || 类型 || 说明 || 版本 ||");
        String[] strArray = str.split("\n");
        String note = "";
        String[] strArray2;
        for (String tmp : strArray){
            if (tmp.contains("//") && tmp.contains(";")){
                strArray2 = tmp.trim().split("//");
                note = strArray2[1];
                strArray2 = tmp.trim().split(" ");
                System.out.println("| " + strArray2[2].substring(0, strArray2[2].length()-1) + " | " + strArray2[1] + " | " + note + " | |");
            }
            else if (tmp.contains("//")){
                note = tmp.trim().substring(2);
            }
            else if (tmp.contains(";")){
                strArray2 = tmp.trim().split(" ");
                System.out.println("| " + strArray2[2].substring(0, strArray2[2].length()-1) + " | " + strArray2[1] + " | " + note + " | |");
            }
        }
    }
}

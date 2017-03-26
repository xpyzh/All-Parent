package util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by hzsunguanjun on 2017/3/10.
 */
public class GenSetter {
    public static void main(String[] args){
        String varName = "settleVo";
        String str = "  //合约唯一标识\n" +
                "    private Long id;\n" +
                "\n" +
                "    //供应商名称\n" +
                "    private String supplierName;\n" +
                "\n" +
                "    //主合同标记（1:主合同，2:补充协议)\n" +
                "    private Integer masterFlag;\n" +
                "\n" +
                "    //合同类型-采购合同(-1:否,1:是)\n" +
                "    private Integer purchaseContractFlag;\n" +
                "\n" +
                "    //合同类型-服务合同(-1:否,1:是)\n" +
                "    private Integer serviceContractFlag;\n" +
                "\n" +
                "    //合同类型-服务合同内容(多选json数组[1:\"质检\",2:\"设计\",3:\"仓库物流\",4:\"摄影\",5:\"模特\",6:\"清关\",7:\"其他\"]\n" +
                "    private Integer serviceContractContent;\n" +
                "\n" +
                "    //合约性质（1:单次合同，2:框架合同）\n" +
                "    private Integer character;\n" +
                "\n" +
                "    //合同编号\n" +
                "    private String contractNo;\n" +
                "\n" +
                "    //合同名称\n" +
                "    private String contractName;\n" +
                "\n" +
                "    //主合同编号\n" +
                "    private String masterContractNo;\n" +
                "\n" +
                "    //合同签订日期\n" +
                "    private Long signingDate;\n" +
                "\n" +
                "    //合同生效日期\n" +
                "    private Long effectDate;\n" +
                "\n" +
                "    //合同失效日期\n" +
                "    private Long expiryDate;\n" +
                "\n" +
                "    //合同失效日期(顺延后的真实日期)\n" +
                "    private Long expiryRealDate;\n" +
                "\n" +
                "    //合约生效标记(-1:作废,0:未生效，1:生效)\n" +
                "    private Integer activeFlag;";

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

package util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by hzsunguanjun on 2017/3/10.
 */
public class GenSetter {
    public static void main(String[] args){
        String varName = "settleVo";
        String str = "  //用户id\n" +
                "    private Long id;\n" +
                "\n" +
                "    //最后登录时间\n" +
                "    private Long lastLoginTime;\n" +
                "\n" +
                "    //首次登陆时间\n" +
                "    private Long firstLoginTime;\n" +
                "\n" +
                "    //是否锁定, (0: 解锁, 1: 锁定)\n" +
                "    private Boolean locked;\n" +
                "\n" +
                "    //用户uid, 也即用户邮箱名\n" +
                "    private String uid;\n" +
                "\n" +
                "    //urs主账号\n" +
                "    private String mainAccount;\n" +
                "\n" +
                "    //用户名\n" +
                "    private String realName;\n" +
                "\n" +
                "    //所属快递公司\n" +
                "    private String company;\n" +
                "\n" +
                "    //创建时间\n" +
                "    private Long createTime;\n";

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

package util;

/**
 * Created by hzsunguanjun on 2017/3/21.
 */
public class GenVo {
    public static void main(String[] args){
        String str = "| id | Long | 主键索引 | |\n" +
                "| setupId | Long | 立项ID | |\n" +
                "| type | int | 立项类型(0:新品立项,1:老品新增供应商,3:特殊商品免立项) | |\n" +
                "| firmCategory | [NcItemFirmCategoryVO |yanxuan:PMS_NcItemFirmCategoryVO] | 物理类目 | |\n" +
                "| sellCategory | List<[NcItemSellCategoryVO |yanxuan:PMS_NcItemSellCategoryVO]> | 销售类目 | |\n" +
                "| itemId | Long | 商品ID | |\n" +
                "| itemName | String | 商品名称 | |\n" +
                "| pictures | List<[UploadFileAddrVO|yanxuan:PMS_UploadFileAddrVO]> | 样品图片 | |\n" +
                "| haveSupplier | bool | 是否已经有供应商 | |\n" +
                "| supplier | [NcItemSupplierVO|yanxuan:PMS_NcItemSupplierVO] | 供应商 | |\n" +
                "| refBrands | List<[NcBrandVO|yanxuan:PMS_NcBrandVO]> | 参考品牌 | |\n" +
                "| refMinPrice | Double | 市场参考价，区间下限 | |\n" +
                "| refMaxPrice | Double | 市场参考价，区间上限 | |\n" +
                "| haveSample | Integer | 是否有实物样(-1:无效,0:无,1:有) | |\n" +
                "| sampleDesc | String | 样品描述 | |\n" +
                "| reason | String | 新品开发理由 | |\n" +
                "| expectPrice | Double | 预期售价 | |\n" +
                "| expectOnelineDate | Long | 预期上线日期 | |\n" +
                "| bsUserId | Long | 提交商务主管用户ID | |\n" +
                "| bsUserEmail | String | 提交商务主管用户邮箱 | |\n" +
                "| traceUserId | Long | 跟进商务用户ID | |\n" +
                "| traceUserEmail | String | 跟进商务用户邮箱 | |\n" +
                "| traceUserName | String | 跟进商务姓名 | |\n" +
                "| setupUserId | Long | 立项用户ID | |\n" +
                "| setupUserName | String | 立项用户名称 | |\n" +
                "| setupUserEmail | String | 立项用户邮箱 | |\n" +
                "| warehouses | List<[NcItemWarehouseVO|yanxuan:PMS_NcItemWarehouseVO]> | 送货仓库 | |\n" +
                "| specs | List<[NcItemSpecVO|yanxuan:PMS_NcItemSpecVO]> | 规格 | |\n" +
                "| sellTimeSlot | List<int> | 可售卖时间段 | |\n" +
                "| valiCtl | Integer | 商品参加有效期管控(-1:无效,0:不参加,1;参加) | |\n" +
                "| remark | String | 备注(当前用于特殊商品发布) | |\n" +
                "| createTime | Long | 立项创建时间 | |\n" +
                "| submitTime | Long | 立项提交时间 | |\n" +
                "| applyTerminateUserId | Long | 申请终止人id | |\n" +
                "| applyTerminateUserEmail | String | 申请终止人邮箱 | |\n" +
                "| applyTerminateTime | Long | 申请终止时间 | |\n" +
                "| leaderConfirmProof | List<[UploadFileAddrVO|yanxuan:PMS_UploadFileAddrVO]> | 领导确认凭证 | |";

        String[] strArr = str.split("\\n");
        String varName;
        String varType;
        String note;
        StringBuffer sb = new StringBuffer();
        for (String tmp : strArr){
            tmp.replaceAll("\\\\", ""); //去掉多余的\
            String[] strArr2 = tmp.split("\\|");
            if (tmp.contains("[")){
                varName = strArr2[1];
                varType = strArr2[2].replaceAll("\\[","");
                if (varType.contains("<")){
                    varType += ">";
                }
                note = strArr2[4];
            }
            else {
                varName = strArr2[1];
                varType = strArr2[2];
                note = strArr2[3];
            }

            sb.append("private " + varType.trim() + " " + varName.trim() + "; //" + note.trim() + "\n");
        }

        System.out.println(sb);
    }
}

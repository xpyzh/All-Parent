/**
* @(#)PlainPackageBean.java, 2018年04月23日
*
* Copyright 2016 Netease, Inc. All rights reserved.
* NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package fastjson.model;

/**
 * @author mler
 */

public class PlainPackageBean  {

//    private PriceDetailBean priceDetail;

//    private ReceiverAddressBean address;

//    private List<PlainSkuBean> skuList;

//    private Map<String, String> extInfoMap;

    private String channelName;

    private String buyerAccount;

//    private PlainOrderBean plainOrder;

    private String outNum;

    private long expCreateTime;

    private String fatherId;

    private String storeHouseName;

    private boolean dispatched;


//    private List<PlainPackageBean> subPackageList;

//    public Map<String, String> getExtInfoMap() {
//        return extInfoMap;
//    }
//
//    public void setExtInfoMap(Map<String, String> extInfoMap) {
//        this.extInfoMap = extInfoMap;
//    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }

    public long getExpCreateTime() {
        return expCreateTime;
    }

    public void setExpCreateTime(long expCreateTime) {
        this.expCreateTime = expCreateTime;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getStoreHouseName() {
        return storeHouseName;
    }

    public void setStoreHouseName(String storeHouseName) {
        this.storeHouseName = storeHouseName;
    }

    public boolean isDispatched() {
        return dispatched;
    }

    public void setDispatched(boolean dispatched) {
        this.dispatched = dispatched;
    }

//    public List<PlainPackageBean> getSubPackageList() {
//        return subPackageList;
//    }
//
//    public void setSubPackageList(List<PlainPackageBean> subPackageList) {
//        this.subPackageList = subPackageList;
//    }
}

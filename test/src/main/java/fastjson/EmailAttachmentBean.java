/**
 * @(#)EmailAttachmentBean.java, 2015-12-14.
 *
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fastjson;

/**
 * bean对象： 邮件附件
 * 
 * @author machao
 * 
 */
public class EmailAttachmentBean {

	protected String name;

	protected String cid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
}

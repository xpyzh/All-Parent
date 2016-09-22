/**
 * @(#)EmailMessageBean.java, 2015-12-12.
 *
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fastjson;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * bean对象：邮件
 *
 * @author hzzhangyong
 *
 */
public class EmailMessageBean {

	

	private String smtpFrom;

	/**
	 * 收件人列表
	 */
	private Collection<EmailContactBean> to = new LinkedHashSet<>();
	/**
	 * 显示的收件人列表
	 */
	private Collection<EmailContactBean> toName = new LinkedHashSet<>();
	/**
	 * 抄送人
	 */
	private Collection<EmailContactBean> cc = new LinkedHashSet<>();
	/**
	 * 密送人
	 */
	private Collection<EmailContactBean> bcc = new LinkedHashSet<>();
	/**
	 * 普通附件
	 */
	private Collection<EmailAttachmentBean> attachments = new LinkedHashSet<>();
	/**
	 * 主题
	 */
	private String subject = null;
	/**
	 * 主题编码方式
	 */
	private String subjectEncoding = "utf-8";
	/**
	 * 正文
	 */
	private String content = null;
	/**
	 * 正文编码方式
	 */
	private String contentEncoding = "utf-8";
	/**
	 * 邮件地址的字符集编码
	 */
	private String addressEncoding = "utf-8";
	/**
	 * 附件文件名字符集编码
	 */
	private String attachmentEncoding = "utf-8";

	
	public Collection<EmailContactBean> getTo() {
		return to;
	}

	public void setTo(Collection<EmailContactBean> to) {
		this.to = to;
	}

	public Collection<EmailContactBean> getCc() {
		return cc;
	}

	public void setCc(Collection<EmailContactBean> cc) {
		this.cc = cc;
	}

	public Collection<EmailContactBean> getBcc() {
		return bcc;
	}

	public void setBcc(Collection<EmailContactBean> bcc) {
		this.bcc = bcc;
	}

	public Collection<EmailAttachmentBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(Collection<EmailAttachmentBean> attachments) {
		this.attachments = attachments;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectEncoding() {
		return subjectEncoding;
	}

	public void setSubjectEncoding(String subjectEncoding) {
		this.subjectEncoding = subjectEncoding;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getAddressEncoding() {
		return addressEncoding;
	}

	public void setAddressEncoding(String addressEncoding) {
		this.addressEncoding = addressEncoding;
	}

	public String getAttachmentEncoding() {
		return attachmentEncoding;
	}

	public void setAttachmentEncoding(String attachmentEncoding) {
		this.attachmentEncoding = attachmentEncoding;
	}

	/**
	 * 添加收件人
	 *
	 * @param name
	 * @param email
	 */
	public void addTo(String name, String email) {
		EmailContactBean c = new EmailContactBean(name, email);
		to.add(c);
	}

	/**
	 * 添加显示的收件人
	 *
	 * @param name
	 * @param email
	 */
	public void addToName(String name, String email) {
		EmailContactBean c = new EmailContactBean(name, email);
		toName.add(c);
	}

	public Collection<EmailContactBean> getToName() {
		return toName;
	}

	/**
	 * 添加附件
	 *
     * @param cid
	 * @param name
	 * @param type
	 * @param bytes
	 */


	public void addCc(String name, String email) {
		EmailContactBean c = new EmailContactBean(name, email);
		cc.add(c);
	}

	public void addBcc(String name, String email) {
		EmailContactBean c = new EmailContactBean(name, email);
		bcc.add(c);
	}

	public String receiveEmailListStr() {
		StringBuilder sb = new StringBuilder(" tos:[");
		for (EmailContactBean re : to) {
			sb.append(re.getEmail()).append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	public String getReceiveEmailListStr() {
		StringBuilder sb = new StringBuilder(" tos:[");
		for (EmailContactBean re : to) {
			sb.append(re.getEmail()).append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	public String getSmtpFrom() {
		return smtpFrom;
	}

	public void setSmtpFrom(String smtpFrom) {
		this.smtpFrom = smtpFrom;
	}

}

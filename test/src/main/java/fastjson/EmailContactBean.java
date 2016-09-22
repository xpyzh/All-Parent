/**
 * @(#)EmailContactBean.java, 2015-12-14.
 *
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fastjson;

/**
 * bean对象：邮件联系人
 * 
 * @author hzzhangyong
 * 
 */
public class EmailContactBean {

	private String name;
	private String email;
	
	/* 下面两个信息是发件人才有的，需要服务器验证时才会使用*/
	private String username;  // 登录邮件发送服务器的用户名
	private String password;  // 登录邮件发送服务器的密码

	public EmailContactBean(){}
	
	public EmailContactBean(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public EmailContactBean(String name, String email, String username, String password){
	    this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
	}

	public String getName() {
		if (null == name || "".equals(name)) {
			if (null != email) {
				int index = email.indexOf("@");
				name = email.substring(0, index);
			}
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
	public String toString() {
		return "\"" + name + "\"" + "<" + email + ">";
	}

}

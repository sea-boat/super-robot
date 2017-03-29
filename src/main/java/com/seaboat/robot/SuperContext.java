package com.seaboat.robot;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 
 * <pre><b>This context contains all robot status.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class SuperContext {

	private String sessionId;
	
	private Hashtable userDefAttributes = new Hashtable();

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Object getUserDefAttribute(String name) {
		return userDefAttributes.get(name);
	}

	public Enumeration getUserDefAttributeNames() {
		return userDefAttributes.keys();
	}

	public void removeUserDefAttribute(String name) {
		userDefAttributes.remove(name);
	}

	public void setUserDefAttribute(String name, Object value) {
		userDefAttributes.put(name, value);
	}
}

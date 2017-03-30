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

	private Hashtable attributes = new Hashtable();

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Object getAttributes(String name) {
		return attributes.get(name);
	}

	public Enumeration getAttributeNames() {
		return attributes.keys();
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}
}

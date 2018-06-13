package com.seaboat.robot.util;

/**
 * 
 * <pre><b>QA. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class QA {

	private long id;

	private String oriPattern;

	private String[] pattern;

	private String template;

	public QA(String _pattern, String template) {
		this.oriPattern = _pattern;
		this.pattern = _pattern.replace(" ", "").split("");
		for (int i = 0; i < pattern.length; i++)
			pattern[i] = pattern[i].trim();
		this.template = template;
	}

	public String[] getPattern() {
		return pattern;
	}

	public String getOriPattern() {
		return oriPattern;
	}

	public String getTemplate() {
		return template;
	}

	public String toString() {
		return id + ":" + oriPattern + ":" + template;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}

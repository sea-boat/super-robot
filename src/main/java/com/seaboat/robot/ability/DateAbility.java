package com.seaboat.robot.ability;

import java.text.SimpleDateFormat;
/**
 * 
 * <pre><b>DateAbility provides date function. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class DateAbility implements Ability {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String process() {
		try {
			return format.format(new java.util.Date());
		} catch (Exception e) {
		}
		return "";
	}
}

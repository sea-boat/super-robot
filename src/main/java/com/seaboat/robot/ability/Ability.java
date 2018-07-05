package com.seaboat.robot.ability;

import com.seaboat.robot.SuperContext;

/**
 * 
 * <pre><b>Ability interface. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public interface Ability {
	/**
	 * process request.
	 * 
	 * @return a response string,return null if fail to process.
	 */
	public String process();

	/**
	 * process request.
	 * 
	 * @return a response string,return null if fail to process.
	 */
	public String process(SuperContext context);

}

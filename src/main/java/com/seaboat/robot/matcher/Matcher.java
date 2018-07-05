package com.seaboat.robot.matcher;

import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2017-12-04
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>matcher interface.</p>
 */
public interface Matcher {

	public String match(String intent, String question);

	public void initMatcher(List<String> files);

}

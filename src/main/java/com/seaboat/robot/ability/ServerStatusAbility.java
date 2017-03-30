package com.seaboat.robot.ability;

import java.text.SimpleDateFormat;
import java.util.List;

import com.seaboat.robot.SuperContext;
import com.seaboat.robot.util.Constants;

/**
 * 
 * <pre><b>ServerStatusAbility provides some functions to get server status. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class ServerStatusAbility implements Ability {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String process() {
		return null;
	}

	public String process(SuperContext context) {
		List<String> sentences = (List<String>) context.getAttributes(Constants.SENTENCES);
		for(String s : sentences)
			System.out.println(s);
		return "haha";
	}
}

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
		@SuppressWarnings("unchecked")
		List<String> sentences = (List<String>) context.getAttributes(Constants.SENTENCES);
		for (String s : sentences)
			System.out.println(s);
		String response = "<br/><div><table align=\"left\" width=\"100%\" border=1 bordercolor=\"#523322\">"
				+ "<tbody><tr><td colspan=\"3\" align=\"center\">服务器状态</td></tr>"
				+ "<tr><td width=\"30%\" height=\"25\">IP</td><td >cpu</td><td >内存</td></tr>"
				+ "<tr><td width=\"30%\">10.103.23.101</td> <td width=\"30%\">12%</td> <td>1254456kB</td></tr>"
				+ "</tbody></table></div><br/>";
		;
		return response;
	}
}

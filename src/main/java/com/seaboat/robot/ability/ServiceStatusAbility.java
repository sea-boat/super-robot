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
public class ServiceStatusAbility implements Ability {

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
				+ "<tbody><tr><td colspan=\"3\" align=\"center\">服务状态</td></tr>"
				+ "<tr><td width=\"30%\" height=\"25\">服务名</td><td >qps</td><td >rpc失败次数</td></tr>"
				+ "<tr><td width=\"30%\">支付服务</td> <td width=\"30%\">2000</td> <td>50</td></tr>"
				+ "</tbody></table></div><br/>";
		return response;
	}
}

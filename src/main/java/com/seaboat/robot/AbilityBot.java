package com.seaboat.robot;

import java.util.HashMap;
import java.util.Map;

import com.seaboat.robot.ability.Ability;
import com.seaboat.robot.ability.DateAbility;
import com.seaboat.robot.ability.MnistAbility;
import com.seaboat.robot.ability.ServerStatusAbility;
import com.seaboat.robot.ability.ServiceStatusAbility;

/**
 * 
 * <pre><b>AbilityBot has all kinds of abilities. According to the input,AbilityBot will match the most similar ability. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class AbilityBot {

	public Map<String, Ability> abilityMap = new HashMap<String, Ability>();

	public AbilityBot() {
		abilityMap.put("DateAbility", new DateAbility());
		abilityMap.put("ServerStatusAbility", new ServerStatusAbility());
		abilityMap.put("MnistAbility", new MnistAbility());
		abilityMap.put("ServiceStatusAbility", new ServiceStatusAbility());
	}

	public Ability searchAbility(String input) {
		return null;
	}
}

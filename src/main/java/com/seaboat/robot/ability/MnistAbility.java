package com.seaboat.robot.ability;

import java.awt.image.BufferedImage;

import com.seaboat.robot.SuperContext;
import com.seaboat.robot.util.Constants;
import com.seaboat.robot.util.KNN;

/**
 * 
 * <pre><b>MnistAbility provides an ability of recognizing handwriting picture. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class MnistAbility implements Ability {

	public String process() {
		// TODO Auto-generated method stub
		return null;
	}

	public String process(SuperContext context) {
		BufferedImage in = (BufferedImage) context
				.getAttributes(Constants.PICTURE);
		String path = (String) context.getAttributes(Constants.PATH);
		return String.valueOf(KNN.predict(in, path));
	}
}

package com.seaboat.robot;

import java.sql.SQLException;

import org.junit.Test;

import com.seaboat.robot.util.DAO4H2;
import com.seaboat.robot.util.QA;

public class AppTest {

	@Test
	public void TestQA() {
		try {
			DAO4H2.insertQA(new QA("haha", "hehe"));
			for (QA qa : DAO4H2.getAllQA())
				System.out.println(qa);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestRobot() throws SQLException {
		DAO4H2.clearQA();
		SuperEngine engine = SuperEngine.getInstance();
		engine.addQA(new QA("* 汪 洋 *", "呵 呵"));
		System.err.println(engine.respond("汪洋哥"));
		System.err.println(engine.respond("汪 洋 哥"));
		engine.addQA(new QA("*龙 * 传人*", "没错"));
		System.err.println(engine.respond("我是龙的de传人"));
		System.err.println(engine.respond("我们是龙的传人"));
	}

}

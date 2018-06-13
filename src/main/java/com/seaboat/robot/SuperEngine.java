package com.seaboat.robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.seaboat.robot.ability.Ability;
import com.seaboat.robot.matcher.DefaultMatcher;
import com.seaboat.robot.matcher.Matcher;
import com.seaboat.robot.util.DAO4H2;
import com.seaboat.robot.util.QA;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.aiml.Category;
import bitoflife.chatterbean.aiml.Pattern;
import bitoflife.chatterbean.aiml.Template;
import bitoflife.chatterbean.parser.AliceBotParser;
import bitoflife.chatterbean.parser.AliceBotParserConfigurationException;
import bitoflife.chatterbean.parser.AliceBotParserException;
import bitoflife.chatterbean.util.Searcher;

/**
 * 
 * <pre><b>SuperRobot's engine,this engine contains AliceBot and AbilityBot. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class SuperEngine implements Engine {

	private AliceBot bot;

	private AliceBot endingBot;

	private AbilityBot abilityBot;

	private SessionManager manager;

	private String word2vecPath;

	private Matcher matcher;

	private List<String> qaFileList;

	private static SuperEngine instance = new SuperEngine();

	private SuperEngine() {
		initAliceBot();
		initAbilityBot();
		initSessionManager();
	}

	public static SuperEngine getInstance() {
		return instance;
	}

	public void initMatcher() {
		matcher = new DefaultMatcher(word2vecPath);
		matcher.initMatcher(qaFileList);
	}

	public void setWord2vecPath(String word2vecPath) {
		this.word2vecPath = word2vecPath;
	}

	public void setQaFileList(List list) {
		this.qaFileList = list;
	}

	public SessionManager getSessionManager() {
		return manager;
	}

	private void initSessionManager() {
		manager = SessionManager.getInstance();
	}

	private void initAbilityBot() {
		this.abilityBot = new AbilityBot();
	}

	private void initAliceBot() {
		Searcher searcher = new Searcher();
		String path = System.getProperty("user.dir");
		try {
			AliceBotParser parser = new AliceBotParser();
			// bot =
			// parser.parse(this.getClass().getResourceAsStream("/resources/context.xml"),
			// this.getClass().getResourceAsStream("/resources/splitters.xml"),
			// this.getClass().getResourceAsStream("/resources/substitutions.xml"),
			// searcher.search(System.getProperty("user.dir")+"/resources/corpus/"));
			// endingBot =
			// parser.parse(this.getClass().getResourceAsStream("/resources/context.xml"),
			// this.getClass().getResourceAsStream("/resources/splitters.xml"),
			// this.getClass().getResourceAsStream("/resources/substitutions.xml"),
			// searcher.searchEnding(System.getProperty("user.dir")+"/resources/corpus/ending/"));
			this.bot = parser.parse(new FileInputStream(path + "/resources/context.xml"),
					new FileInputStream(path + "/resources/splitters.xml"),
					new FileInputStream(path + "/resources/substitutions.xml"),
					searcher.search(path + "/resources/corpus/"));
			this.endingBot = parser.parse(new FileInputStream(path + "/resources/context.xml"),
					new FileInputStream(path + "/resources/splitters.xml"),
					new FileInputStream(path + "/resources/substitutions.xml"),
					searcher.searchEnding(path + "/resources/corpus/ending/"));

			for (QA qa : DAO4H2.getAllQA())
				addQA(qa);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (AliceBotParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AliceBotParserConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String respond(String input) {
		String response = bot.respond(input);
		if (response != null && !response.equals("#"))
			return response;
		Ability ability = abilityBot.searchAbility(input);
		response = ability.process();
		if (response == null)
			response = "不好意思，不懂你的意思，可以让我的主人先教我！";
		return response;
	}

	public String respond(String input, String sessionId) {
		String response = bot.respond(input);
		if (response != null && !response.equals("#"))
			return response;
		SuperContext context = manager.getContext(sessionId);
		String intent = "khala";
		System.out.println(input);
		response = matcher.match(intent, input);
		System.out.println(response);
		// Ability ability = abilityBot.searchAbility(input);
		// if (ability != null)
		// response = ability.process(context);
		if (response == null || response.equals("#"))
			response = endingBot.respond(input);
		if (response == null || response.equals("#"))
			response = "不好意思，不懂你的意思，可以让我的主人先教我！";
		return response;
	}

	public void addQA(QA qa) {
		try {
			DAO4H2.insertQA(qa);
			Category c = new Category(new Pattern(qa.getPattern()), new Template(qa.getTemplate()));
			bot.getGraphmaster().append(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteQA(long id) {
		try {
			DAO4H2.deleteQA(id);
			initAliceBot();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

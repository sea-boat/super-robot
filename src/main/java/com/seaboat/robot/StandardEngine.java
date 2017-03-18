package com.seaboat.robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.parser.AliceBotParser;
import bitoflife.chatterbean.parser.AliceBotParserConfigurationException;
import bitoflife.chatterbean.parser.AliceBotParserException;
import bitoflife.chatterbean.util.Searcher;

public class StandardEngine implements Engine {

	public AliceBot bot;

	public AbilityBot abilityBot;

	public StandardEngine() {
		initAliceBot();
		initAbilityBot();
	}

	private void initAbilityBot() {
		this.abilityBot = new AbilityBot();
	}

	private void initAliceBot() {
		Searcher searcher = new Searcher();
		AliceBot bot = null;
		try {
			AliceBotParser parser = new AliceBotParser();
			bot = parser.parse(
					this.getClass()
							.getResourceAsStream("/resources/context.xml"),
					this.getClass().getResourceAsStream(
							"/resources/splitters.xml"),
					this.getClass().getResourceAsStream(
							"/resources/substitutions.xml"), searcher
							.search("/resources/chinese/"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (AliceBotParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AliceBotParserConfigurationException e) {
			e.printStackTrace();
		}
		this.bot = bot;
	}

	public String respond(String input) {
		String response = bot.respond(input);
		if (response != null && !response.equals("#"))
			return response;
		response = abilityBot.searchAbility(input);
		return response;
	}

}

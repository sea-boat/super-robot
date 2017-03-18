package com.seaboat.robot.ability;

import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;
import bitoflife.chatterbean.aiml.TemplateElement;

public class DateAbility implements Ability {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String process() {
		try {
			return format.format(new java.util.Date());
		} catch (Exception e) {
		}
		return "";
	}
}

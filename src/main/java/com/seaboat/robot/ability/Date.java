package com.seaboat.robot.ability;

import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;
import bitoflife.chatterbean.aiml.TemplateElement;

public class Date extends TemplateElement implements Ability{

	private final SimpleDateFormat format = new SimpleDateFormat();
	private String formatStr = "";

	public Date(Attributes attributes) {
		formatStr = attributes.getValue(0);
	}

	public String process(Match match) {
		try {
			format.applyPattern(formatStr);
			return format.format(new java.util.Date());
		} catch (Exception e) {
			return defaultDate(match);
		}
	}

	private String defaultDate(Match match) {
		try {
			format.applyPattern((String) match.getCallback().getContext()
					.property("predicate.dateFormat"));
			return format.format(new java.util.Date());
		} catch (NullPointerException e) {
			return "";
		}
	}
}

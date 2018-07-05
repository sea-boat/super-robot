package bitoflife.chatterbean.aiml;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Br extends TemplateElement {
	/*
	Constructors
	*/

	public Br(Attributes attributes) {
	}

	public Br(Object... children) {
		super(children);
	}

	/*
	Methods
	*/

	public String process(Match match) {
		return "";
	}
}
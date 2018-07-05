package bitoflife.chatterbean.util;

import java.util.regex.Matcher;

public class Translate {
	public static String translateString(String input) {
		StringBuffer newStr = new StringBuffer("");
		String strTemp = "";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("[A-Za-z0-9\\s]");
		char[] chars = new char[1];
		Matcher m;
		for (int i = 0; i < input.length(); i++) {
			chars[0] = input.charAt(i);
			m = p.matcher(new String(chars));
			if (!m.matches())
				newStr.append(" ").append(input.charAt(i)).append(" ");
			else
				newStr.append(input.charAt(i));
		}
		strTemp = newStr.toString().replaceAll("\\s{2,}", " ");
		strTemp = strTemp.replaceAll("^\\s*|\\s*$", "");
		return strTemp;
	}
}

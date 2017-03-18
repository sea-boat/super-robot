package com.seaboat.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Boostrap {
	public static String input() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("我：");
		String input = "";
		try {
			input = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	public static void main(String[] args) throws Exception {
		StandardEngine engine = new StandardEngine();
		System.err.println("super-robot：>" + engine.respond("欢迎"));
		while (true) {
			String input = input();
			System.err.println("super-robot>" + engine.respond(input));
		}

	}
}

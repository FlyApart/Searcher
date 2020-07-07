package com.softeq.listener;

import lombok.Data;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Data
public class ConsoleListener {
	private static final String SET_URL = "Set predefined URL";
	private static final String SET_LIMIT_CRAWLING = "Set max visited pages";
	private static final String SET_TERMS = "Set terms";

	private final Scanner sc = new Scanner (System.in);
	private String URL;
	private int limitPages;
	private List<String> terms;

	public ConsoleListener (){
		System.out.println (SET_URL);
		URL = sc.nextLine ();

		System.out.println (SET_LIMIT_CRAWLING);
		limitPages = Integer.parseInt (sc.nextLine ());

		System.out.println  (SET_TERMS);
		terms = Arrays.asList (sc.nextLine ().split (", "));
	}


}

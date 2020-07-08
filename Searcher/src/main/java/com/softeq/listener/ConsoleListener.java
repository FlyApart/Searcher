package com.softeq.listener;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//console menu for entering initial values
@Getter
public class ConsoleListener {
	private static final Scanner sc = new Scanner (System.in);
	//parameters for launching the application
	private static final String SET_URL = "Set predefined URL";
	private static final String SET_LIMIT_CRAWLING = "Set max visited pages";
	private static final String SET_TERMS = "Set terms";
	private String URL;
	private int limitPages;
	private List<String> terms;

	public ConsoleListener () {
		consoleMenu ();
	}

	//scanning of input data
	public void consoleMenu () {
		System.out.println (SET_URL);
		URL = sc.nextLine ();

		System.out.println (SET_LIMIT_CRAWLING);
		try{
			limitPages = Integer.parseInt (sc.nextLine ());
		}catch (NumberFormatException e){
			System.out.println ("Incorrect value!");
			consoleMenu ();
			return;
		}

		System.out.println (SET_TERMS);
		terms = Arrays.asList (sc.nextLine ()
		                         .split (", "));
	}
}

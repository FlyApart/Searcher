package com.softeq.listener;

import com.softeq.crawler.logger.Logger;
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
		Logger.logInf.info (SET_URL);
		URL = sc.nextLine ();

		Logger.logInf.info (SET_LIMIT_CRAWLING);
		try{
			limitPages = Integer.parseInt (sc.nextLine ());
		}catch (NumberFormatException e){
			Logger.logInf.info ("Incorrect value!");
			consoleMenu ();
			return;
		}

		Logger.logInf.info (SET_TERMS);
		terms = Arrays.asList (sc.nextLine ()
		                         .split (", "));
	}
}

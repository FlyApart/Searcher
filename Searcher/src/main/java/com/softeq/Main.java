package com.softeq;

import com.softeq.crawler.Crawler;
import com.softeq.listener.ConsoleListener;

public class Main {

	//application launch
	public static void main (String[] args) {
		ConsoleListener cl = new ConsoleListener ();
		while (cl.isWork ()){
			Crawler crawler = new Crawler (cl.getURL (), cl.getLimitPages (), cl.getTerms ());
			crawler.crawling ();
			cl.stopApp ();
		}

	}
}

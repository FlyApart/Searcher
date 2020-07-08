package com.softeq;

import com.softeq.crawler.Crawler;
import com.softeq.listener.ConsoleListener;

import java.io.IOException;

public class Main {

	//application launch
	public static void main (String[] args) throws IOException {
		ConsoleListener cl = new ConsoleListener ();
		Crawler crawler = new Crawler (cl.getURL (), cl.getLimitPages (), cl.getTerms ());
		crawler.crawling ();

	}
}

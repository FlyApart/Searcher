package com.softeq.crawler;

import com.softeq.crawler.util.CheckPage;
import com.softeq.crawler.util.CheckURL;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Crawler {

	CheckPage checkPage = new CheckPage ();

	private HashMap<String, List<Integer>> foundLinks = new HashMap<>();
	private Set<String> forbiddenLinks = new HashSet<> ();
	private Queue<String> crawledList = new ArrayDeque<> (500);

	private int limitPages;
	private List<String> terms;

	public Crawler (String URL, int limitPages, List<String> terms) {
		this.limitPages = limitPages;
		this.terms = terms;
		crawledList.add (URL);

	}

	public void crawling() {
		for (; limitPages > 0 && !crawledList.isEmpty (); limitPages--){
			URL link = CheckURL.createURL (crawledList.poll ());

			if (link != null){
			String currentPage = checkPage.downloadPage (link);

				if (currentPage != null){
					foundLinks.put (currentPage,checkPage.findWords (currentPage,terms));
				}
			}
		}
	}



}

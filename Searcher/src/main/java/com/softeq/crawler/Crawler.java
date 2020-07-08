package com.softeq.crawler;

import com.softeq.crawler.logger.Logger;
import com.softeq.crawler.util.CheckPage;
import com.softeq.crawler.util.CheckURL;
import com.softeq.crawler.util.SerializeUtil;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


//Perform a scan by searching for a given string.
public class Crawler{
	private final CheckPage checkPage;
	private final CheckURL checkURL = new CheckURL ();

	//All pages on which matches are found
	private final HashMap<String, List<Integer>> foundLinks = new HashMap<> ();
	//All checked pages
	private final Set<String> forbiddenLinks = new HashSet<> ();
	//Pages in the next inspection
	private final Queue<String> crawledList = new ArrayDeque<> (10000);
	//Searched words per page
	private final List<String> terms;
	//Number of pages to view
	private int limitPages;

	public Crawler (String url, int limitPages, List<String> terms) {
		this.limitPages = limitPages;
		this.terms = terms;
		checkPage = new CheckPage (terms);
		//Add the start URL to the search list.
		crawledList.add (url);
	}

	public void crawling () {
		//Passing in turn until new links are found or the set limit of visited pages ends
		for (; limitPages > 0 && !crawledList.isEmpty (); limitPages--) {
			//Convert string to link and check it
			URL link = checkURL.createURL (crawledList.poll ());

			if (link != null) {
				//Search for words and links
				checkPage.finder (link, forbiddenLinks, terms);
				//Check for matching words
				if (checkPage.isWordMatch ()) {
					List<Integer> countWords = checkPage.getCountWords ();
					foundLinks.put (link.toString (), new ArrayList<> (countWords));
				}
				//Adding found links
				Set<String> searchLinks = checkPage.getSearchLinks ();
				if (searchLinks != null){
					crawledList.addAll (searchLinks);
				}
				//Add the current URL to the list of already used
				forbiddenLinks.add (link.toString ());
			} else break;

		}
		showResult ();
	}

	public void showResult(){
		Map<String, List<Integer>> sortedMap = sorted ();
		SerializeUtil.serialize (foundLinks, false);
		SerializeUtil.serialize (sortedMap,true);
		sortedMap.forEach ((k,v)-> Logger.logInf.info (String.format ("%s%s%s",v," : ",k)));
	}

	private Map<String, List<Integer>> sorted(){
		return foundLinks.entrySet()
				            .stream()
				            .sorted(Comparator.comparingInt (e -> e.getValue ()
				                                                   .stream ()
				                                                   .reduce (Integer.MAX_VALUE,(o1,o2)-> o1-o2, Integer::sum)))
				            .limit (10)
				            .collect (Collectors.toMap (Map.Entry::getKey, Map.Entry::getValue,
						            (oldValue, newValue)-> oldValue, LinkedHashMap::new));
	}


}
//           Tesla, Musk, Gigafactory, Elon Mask
//           https://en.wikipedia.org/wiki/Elon_Musk
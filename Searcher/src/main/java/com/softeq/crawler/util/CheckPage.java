package com.softeq.crawler.util;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class CheckPage {
	private final Pattern linkPattern = Pattern.compile ("\\s* (?i)href\\s* =\\s* (\"([^\"]* \")|'[^']* '|([^'\">\\s]+))");
	private Matcher matcher;

	public String downloadPage(URL link){

		try (BufferedReader reader = new BufferedReader(new InputStreamReader (link.openStream()))){
			String lines;

			StringBuilder currentPage = new StringBuilder();
			while ((lines= reader.readLine()) != null) {
				currentPage.append(lines);
			}
			return currentPage.toString();
		} catch (IOException e) {
			return null;
		}
	}

	public List<Integer> findWords(String page, List<String> terms){
		List<Integer> countWords = new ArrayList<> (terms.size ());
		for (String term : terms) {
			countWords.add (StringUtils.countMatches (page, term));
		}
		return countWords;
	}

	public Set<String> findLinks(String page, Set<String> forbiddenLinks){
		Set<String> links = new HashSet<> ();
		matcher = linkPattern.matcher (page);
		while (matcher.find ()){
			String newLink = matcher.group ();


		}
		return links;
	}
}

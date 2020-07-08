package com.softeq.crawler.util;

import com.softeq.crawler.logger.Logger;
import com.softeq.crawler.parser.HtmlParser;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.html.HTML;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

// Search through specified conditions
@Getter
public class CheckPage {
	// Search Criteria
	private String searchWords;
	private Set<String> searchLinks;
	private List<Integer> countWords;
	private CheckURL checkURL = new CheckURL ();
	private Matcher matcher;
	private StringBuilder page;
	// Is at least 1 word match found
	boolean wordMatch;


	public CheckPage (List<String> terms) {
		this.countWords = new ArrayList<> (terms.size ());
	}

	// Run search
	public void finder (URL currentUrl, Set<String> forbiddenLinks, List<String> terms) {
		findLinks (currentUrl, forbiddenLinks);
		findWords (currentUrl, terms);
	}

	// Find all word matches on the page
	private void findWords (URL currentUrl, List<String> terms) {
		// No matches
		wordMatch = false;
		// Separating text from code
		ParserDelegator p = new ParserDelegator ();
		HtmlParser parser = new HtmlParser (HTML.Tag.HTML);
		try (BufferedReader br = new BufferedReader (new InputStreamReader (currentUrl.openStream ()))) {
			p.parse (br, parser, false);
		} catch (Exception e) {
			Logger.logErr.error (CheckPage.class, e);
			return;
		}
		// Assign the result case insensitive
		searchWords = parser.getComponents ()
		                    .toString ()
		                    .toLowerCase ();
		// Clear the previous result
		countWords.clear ();
		// Search for the given words from the page
		for (String term : terms) {
			int countMatches = StringUtils.countMatches (searchWords, term.toLowerCase ());
			if (countMatches>0){
				wordMatch = true;
			}
			countWords.add (countMatches);
		}
	}

	// Find all links on the loaded page
	public void findLinks (URL currentUrl, Set<String> forbiddenLinks) {
		// Page load
		page = downloadPage (currentUrl);
		if (page == null) {
			return;
		}
		searchLinks = new HashSet<> ();
		// Find the link
		matcher = checkURL.createURLFromPage (page.toString ());
		// Validate each link
		while (matcher.find ()) {
			String newLink = checkURL.validationLink (currentUrl, matcher.group (1));
			if (newLink != null) {
				// A link that has successfully passed validation is added to the list
				searchLinks.add (newLink);
			}
		}
		// Remove from the resulting list of links that have already been used
		searchLinks.removeAll (forbiddenLinks);
	}

	//Download the page with the given URL
	public StringBuilder downloadPage (URL link) {

		//Open the connection at the specified URL for reading.
		try (BufferedReader reader = new BufferedReader (new InputStreamReader (link.openStream ()))) {
			String lines;
			page = new StringBuilder ();
			//Find all the lines where there are links
			while ((lines = reader.readLine ()) != null) {
				if (checkURL.createURLFromPage (lines)
				            .find ()) {
					page.append (lines);
				}
			}
			return page;
			// When an exception occurs, an empty string is returned
		} catch (Exception e) {
			Logger.logErr.error (CheckPage.class, e);
		}
		return null;

	}
}
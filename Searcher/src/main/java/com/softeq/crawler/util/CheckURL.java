package com.softeq.crawler.util;

import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//The class used to convert a string to URL and check its validity
public class CheckURL {

	//Constants and regular expressions used in URL
	private static final String WWW = "://www.";
	private static final String HTTP = "http";
	private static final String SEPARATOR = "://";
	private static final String SINGLE_SEPARATOR = "/";
	private static final String NUMBER_SIGN = "#";
	private final Pattern linkPattern = Pattern.compile ("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
	private final List<String> invalidLinks = Arrays.asList ("mailto:", "javascript");

	//Convert a string to URL
	public URL createURL (String link) {
		try {
			return new URL (link.replace (WWW, SEPARATOR));
		} catch (Exception e) {
			return null;
		}
	}

	//Checking a string for links in it
	public Matcher createURLFromPage (String page) {
		return linkPattern.matcher (page);
	}

	//Parse link and restoration of the absolute path
	public String validationLink (URL currentUrl, String link) {

		//Skip links that are used for mail and javascript
		for (String invalidLink : invalidLinks) {
			if (link.contains (invalidLink)) {
				return null;
			}
		}
		if (link.startsWith (NUMBER_SIGN)) {
			return null;
		}

		//Skip links that point to the given page.
		if (link.contains (NUMBER_SIGN)) {
			link = StringUtils.substringBefore (link, NUMBER_SIGN);
			if (link.length () <= 1) {
				return null;
			}
		}

		//Restoration of the absolute path
		if (!link.startsWith (HTTP)) {
			if (link.startsWith (SINGLE_SEPARATOR)) {
				link = currentUrl.getProtocol ()
				                 .concat (SEPARATOR)
				                 .concat (currentUrl.getHost ())
				                 .concat (link);
			} else {
				String protocol = currentUrl.getProtocol ();
				String file = currentUrl.getFile ();
				String host = currentUrl.getHost ();
				if (file.contains (SINGLE_SEPARATOR)) {
					link = protocol.concat (SEPARATOR)
					               .concat (host)
					               .concat (file.substring (0, file.lastIndexOf (SINGLE_SEPARATOR)))
					               .concat (link);
				} else {
					link = protocol.concat (SEPARATOR)
					               .concat (currentUrl.getHost ())
					               .concat (SINGLE_SEPARATOR)
					               .concat (link);
				}
			}
		}
		return link;
	}
}

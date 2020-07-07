package com.softeq.crawler.util;

import java.net.URL;


public class CheckURL {

	public static URL createURL (String url){
		try {
			return new URL(url.replace ("://www.","://"));
		} catch (Exception e) {
			return null;
		}
	}
}

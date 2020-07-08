package com.softeq.crawler.parser;

import lombok.Getter;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

// Syntactical analyzer
@Getter
public class HtmlParser extends ParserCallback {
	// Whether we are currently in the given tag
	private boolean inTD = false;
	// Searched tag
	private final HTML.Tag tagHTML;
	// Result line
	private final StringBuilder components = new StringBuilder ();

	public HtmlParser (HTML.Tag tagHTML) {
		this.tagHTML = tagHTML;
	}

	// Set start tag
	@Override
	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		if(t.equals(tagHTML)) {
			inTD = true;
		}
	}

	// Set end tag
	@Override
	public void handleEndTag(HTML.Tag t, int pos) {
		if(t.equals(tagHTML)) {
			inTD = false;
		}
	}

	// If we are in the tag, update the resulting string
	@Override
	public void handleText(char[] data, int pos) {
		if(inTD) {
			createStringFromPage(data);
		}
	}

	public void createStringFromPage(char[] data) {
		components.append (data);
	}
}

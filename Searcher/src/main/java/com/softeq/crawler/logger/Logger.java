package com.softeq.crawler.logger;

import org.apache.logging.log4j.LogManager;

public class Logger {
	public final static org.apache.logging.log4j.Logger logErr = LogManager.getLogger("Error");
	public final static org.apache.logging.log4j.Logger logInf = LogManager.getLogger("Info");
}

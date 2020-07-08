package com.softeq.crawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializeUtil {
	private static final String SEPARATOR = " ";
	private static final File file = new File (System.getProperty("user.dir") + System.getProperty("file.separator") + "crawler.csv");
	private static final File sortedFile = new File (System.getProperty("user.dir") + System.getProperty("file.separator") + "sortedCrawler.csv");

	public static void serialize(Map<String, List<Integer>> map, boolean sorted){
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter (new FileOutputStream (sorted ? sortedFile : file), StandardCharsets.UTF_8))){
			for (Map.Entry<String, List<Integer>> entry : map.entrySet ()) {
				StringBuilder builder = new StringBuilder ();
				builder.append (entry.getKey ());
				builder.append (SEPARATOR);
				for (Integer value : entry.getValue ()) {
					builder.append(value);
					builder.append (SEPARATOR);
				}
				bw.write (builder.toString ());
				bw.newLine ();
			}
			bw.flush ();
		}catch (IOException e){

		}
	}
}

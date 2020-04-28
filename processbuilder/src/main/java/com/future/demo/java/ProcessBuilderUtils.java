package com.future.demo.java;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * 
 */
public class ProcessBuilderUtils {
	
	/**
	 * 
	 * @param command
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static List<String> exec(String command) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = processBuilder.start();
		
		InputStream inputStream = null;
		List<String> result = null;
		try {
			inputStream = process.getInputStream();
			result = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
		} catch(Exception ex) {
			throw ex;
		} finally {
			if(inputStream!=null) {
				inputStream.close();
				inputStream = null;
			}
		}
		
		int exit = process.waitFor();  
	    if (exit != 0) {  
	        throw new IOException("failed to execute:" + processBuilder.command() + " with result:" + result);  
	    }
	    return result;
	}
}

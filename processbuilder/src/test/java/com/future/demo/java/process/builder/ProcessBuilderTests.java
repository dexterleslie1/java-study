package com.future.demo.java.process.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.future.demo.java.ProcessBuilderUtils;

/**
 * 
 */
public class ProcessBuilderTests {
	/**
	 * 执行pwd命令
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test
	public void versionOfJava() throws IOException, InterruptedException {
		List<String> results = ProcessBuilderUtils.exec("pwd");
		System.out.println("Command [pwd] with result:" + results);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void changeDirectory() throws IOException, InterruptedException {
		String directory = "target";
		ProcessBuilder processBuilder = new ProcessBuilder("ls");
		processBuilder.directory(new File(directory));
		Process process = processBuilder.start();
		
		InputStream inputStream = null;
		List<String> results = null;
		try {
			inputStream = process.getInputStream();
			results = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
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
	        throw new IOException("failed to execute:" + processBuilder.command() + " with result:" + results);  
	    }
	    System.out.println("Command [ls] under directory [" + processBuilder.directory().getAbsolutePath() + "] with result:" + results);
	}
	
	/**
	 * 
	 */
	@Test
	public void environment() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		Map<String, String> environments = processBuilder.environment();
		StringBuilder builder = new StringBuilder();
		builder.append("Environment variables ");
		for(String keyTemporary : environments.keySet()) {
			builder.append(keyTemporary + "=" + environments.get(keyTemporary));
			builder.append(",");
		}
		System.out.println(builder.toString());
	}
	
	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public void addEnvironments() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		Map<String, String> environments = processBuilder.environment();
		environments.put("EMAIL", "dexterleslie@gmail.com");
		processBuilder.command("/bin/bash", "-c", "echo $EMAIL");
		Process process = processBuilder.start();
		
		InputStream inputStream = null;
		List<String> results = null;
		try {
			inputStream = process.getInputStream();
			results = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
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
	        throw new IOException("failed to execute:" + processBuilder.command() + " with result:" + results);  
	    }
	    System.out.println("Command [/bin/bash -c echo $EMAIL] with result:" + results);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void redirectOutput() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("date");
		processBuilder.redirectErrorStream(true);
		File file = File.createTempFile("tmp", ".tmp");
		processBuilder.redirectOutput(file);
		Process process = processBuilder.start();
		
		// RedirectOutput后InputStream没有数据
		Assert.assertEquals("Should be -1 when redirectOutput", -1, process.getInputStream().read());
		
		int exit = process.waitFor();
	    if (exit != 0) {  
	        throw new IOException("failed to execute:" + processBuilder.command());  
	    }
	    
	    InputStream inputStream = null;
	    try {
	    	inputStream = new FileInputStream(file);
	    	List<String> results = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
	    	System.out.println("Command [date] with results:" + results);
	    } catch (Exception ex) {
	    	throw ex;
	    } finally {
	    	if(inputStream!=null) {
	    		inputStream.close();
	    		inputStream = null;
	    	}
	    }
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void exitValueGreaterThanZero() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("/bin/bash", "-c", "command-none-exists");
		processBuilder.redirectErrorStream(true);
		File file = File.createTempFile("tmp", ".tmp");
		processBuilder.redirectError(file);
		Process process = processBuilder.start();
		
		InputStream inputStream = null;
		List<String> results = null;
		try {
			inputStream = process.getInputStream();
			results = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
		} catch(Exception ex) {
			throw ex;
		} finally {
			if(inputStream!=null) {
				inputStream.close();
				inputStream = null;
			}
		}
		
		System.out.println("Command [/bin/bash -c command-none-exists] with result:" + results);
		
		int exit = process.waitFor();
	    if (exit != 0) {
	    	List<String> reason = null;
		    try {
		    	inputStream = new FileInputStream(file);
		    	reason = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
		    } catch (Exception ex) {
		    	throw ex;
		    } finally {
		    	if(inputStream!=null) {
		    		inputStream.close();
		    		inputStream = null;
		    	}
		    }
	        throw new IOException("failed to execute:" + processBuilder.command() + " with reason " + reason);  
	    }
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test
	public void inheritIO() throws InterruptedException, IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "echo hello");
		processBuilder.inheritIO();
		Process process = processBuilder.start();
		int exitValue = process.waitFor();
		Assert.assertEquals("No errors should be detected", 0, exitValue);
	}
	
	/**
	 * 执行多个命令
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test
	public void multipleCommands() throws InterruptedException, IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "cd .. && pwd && ls");
		processBuilder.inheritIO();
		Process process = processBuilder.start();
		int exitValue = process.waitFor();
		Assert.assertEquals("No errors should be detected", 0, exitValue);
	}
}

package com.future.study.spring.large.scale.solution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserRepositoryJDBCTest {
	private final static Logger logger = LogManager.getLogger(UserRepositoryJDBCTest.class);

	@Test
	public void testInsertByUsingJDBC() throws InterruptedException {
		String sql = "insert into t_user(id,createTime,loginname,nickname,password,phone,email,sex)" +
				" values(?,?,?,?,?,?,?,?)";

		List<Integer> sex = new ArrayList<Integer>();
		sex.add(0);
		sex.add(1);
		sex.add(2);

		Date date1 = new Date();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int j = 0; j < 100 ; j++) {
			final int j1 = j;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Connection conn = null;
					PreparedStatement preparedStatement = null;
					try {
						String driver = "com.mysql.jdbc.Driver";
						String url = "jdbc:mysql://192.168.1.153:3306/backend";
						String username = "root";
						String password = "Root@123";
						Class.forName(driver); //classLoader,加载对应驱动
						conn = (Connection) DriverManager.getConnection(url, username, password);

						Random random = new Random();
						int batchSize = 10000;
						int size = sex.size();
						int start = j1 * batchSize + 1;
						int end = start + batchSize;
						for (int i = start; i < end; i++) {
							preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
							preparedStatement.setInt(1,i);
							preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
							preparedStatement.setString(3, "ln" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(4, "nn" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(5,  RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(6, "135" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(7, "gm" + RandomUtils.getCharacterAndNumber(20) + "@gmail.com");
							int index = random.nextInt(size);
							preparedStatement.setInt(8, sex.get(index));
							preparedStatement.executeUpdate();
							preparedStatement.close();
							preparedStatement = null;
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally {
						if(preparedStatement != null){
							try {
								preparedStatement.close();
								preparedStatement = null;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn != null){
							try {
								conn.close();
								conn = null;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}

		executorService.shutdown();
		while(!executorService.awaitTermination(1, TimeUnit.SECONDS));

		Date date2 = new Date();
		long milliseconds = date2.getTime() - date1.getTime();
		logger.debug("耗时："+ milliseconds + "毫秒");
	}

	@Test
	public void testInsertByUsingJDBCShardingTables() throws InterruptedException {
		int tableCount = 4;
		List<String> sqls = new ArrayList<String>();
		for(int i = 1; i <= tableCount; i++){
			sqls.add("insert into t_user"+i+"(id,createTime,loginname,nickname,password,phone,email,sex) "+
					" values(?,?,?,?,?,?,?,?)");
		}

		List<Integer> sex = new ArrayList<Integer>();
		sex.add(0);
		sex.add(1);
		sex.add(2);

		Date date1 = new Date();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int j = 0; j < 100 ; j++) {
			final int j1 = j;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Connection conn = null;
					PreparedStatement preparedStatement = null;
					try {
						int index1 = j1 % tableCount;
						String sql = sqls.get(index1);
						String driver = "com.mysql.jdbc.Driver";
						String url = "jdbc:mysql://192.168.1.153:3306/backend";
						String username = "root";
						String password = "Root@123";
						Class.forName(driver); //classLoader,加载对应驱动
						conn = (Connection) DriverManager.getConnection(url, username, password);

						Random random = new Random();
						int batchSize = 10000;
						int size = sex.size();
						int start = j1 * batchSize + 1;
						int end = start + batchSize;
						for (int i = start; i < end; i++) {
							preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
							preparedStatement.setInt(1,i);
							preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
							preparedStatement.setString(3, "ln" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(4, "nn" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(5,  RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(6, "135" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(7, "gm" + RandomUtils.getCharacterAndNumber(20) + "@gmail.com");
							int index = random.nextInt(size);
							preparedStatement.setInt(8, sex.get(index));
							preparedStatement.executeUpdate();
							preparedStatement.close();
							preparedStatement = null;
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally {
						if(preparedStatement != null){
							try {
								preparedStatement.close();
								preparedStatement = null;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn != null){
							try {
								conn.close();
								conn = null;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}

		executorService.shutdown();
		while(!executorService.awaitTermination(1, TimeUnit.SECONDS));

		Date date2 = new Date();
		long milliseconds = date2.getTime() - date1.getTime();
		logger.debug("耗时："+ milliseconds + "毫秒");
	}

	@Test
	public void testInsertByUsingJDBCShardDatabases() throws InterruptedException {
		List<String> urls = new ArrayList<String>();
		urls.add("jdbc:mysql://192.168.1.153:3306/backend");
		urls.add("jdbc:mysql://192.168.1.154:3306/backend");
		urls.add("jdbc:mysql://192.168.1.155:3306/backend");
		urls.add("jdbc:mysql://192.168.1.156:3306/backend");

		int databaseCount = urls.size();

		String sql = "insert into t_user(id,createTime,loginname,nickname,password,phone,email,sex)" +
				" values(?,?,?,?,?,?,?,?)";

		List<Integer> sex = new ArrayList<Integer>();
		sex.add(0);
		sex.add(1);
		sex.add(2);

		Date date1 = new Date();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int j = 0; j < 200 ; j++) {
			final int j1 = j;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Connection conn = null;
					PreparedStatement preparedStatement = null;
					try {
						String driver = "com.mysql.jdbc.Driver";
						int index1 = j1 % databaseCount;
						String url = urls.get(index1);
						String username = "root";
						String password = "Root@123";
						Class.forName(driver); //classLoader,加载对应驱动
						conn = (Connection) DriverManager.getConnection(url, username, password);

						Random random = new Random();
						int batchSize = 5000;
						int size = sex.size();
						int start = j1 * batchSize + 1;
						int end = start + batchSize;
						for (int i = start; i < end; i++) {
							preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
							preparedStatement.setInt(1,i);
							preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
							preparedStatement.setString(3, "ln" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(4, "nn" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(5,  RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(6, "135" + RandomUtils.getCharacterAndNumber(20));
							preparedStatement.setString(7, "gm" + RandomUtils.getCharacterAndNumber(20) + "@gmail.com");
							int index = random.nextInt(size);
							preparedStatement.setInt(8, sex.get(index));
							preparedStatement.executeUpdate();
							preparedStatement.close();
							preparedStatement = null;
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally {
						if(preparedStatement != null){
							try {
								preparedStatement.close();
								preparedStatement = null;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(conn != null){
							try {
								conn.close();
								conn = null;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}

		executorService.shutdown();
		while(!executorService.awaitTermination(1, TimeUnit.SECONDS));

		Date date2 = new Date();
		long milliseconds = date2.getTime() - date1.getTime();
		logger.debug("耗时："+ milliseconds + "毫秒");
	}
}
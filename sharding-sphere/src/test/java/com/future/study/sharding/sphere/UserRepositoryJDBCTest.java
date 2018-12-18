package com.future.study.sharding.sphere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Config.class})
public class UserRepositoryJDBCTest {
	private final static Logger logger = LogManager.getLogger(UserRepositoryJDBCTest.class);
	
	@Autowired
	private DataSource dataSource;

	/**
	 * 一个数据节点
	 * 耗时：408341毫秒
	 * 耗时：402182毫秒
	 * 
	 * 4个数据节点
	 * 耗时：198625毫秒
	 * 耗时：205646毫秒
	 * @throws InterruptedException
	 */
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
		for(int j = 0; j < 200 ; j++) {
			final int j1 = j;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Connection conn = null;
					PreparedStatement preparedStatement = null;
					try {
						conn = dataSource.getConnection();

						Random random = new Random();
						int batchSize = 5000;
						int size = sex.size();
						int start = j1 * batchSize + 1;
						int end = start + batchSize;
						for (int i = start; i < end; i++) {
							java.sql.Date date = new java.sql.Date(new Date().getTime());
							String randomString = RandomUtils.getCharacterAndNumber(20);
							String loginname = "ln" + randomString;
							String nickname = "nn" + randomString;
							String phone = "135" + randomString;
							String email = "gm" + randomString + "@gmail.com";
							preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
							preparedStatement.setInt(1,i);
							preparedStatement.setDate(2, date);
							preparedStatement.setString(3, loginname);
							preparedStatement.setString(4, nickname);
							preparedStatement.setString(5, randomString);
							preparedStatement.setString(6, phone);
							preparedStatement.setString(7, email);
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
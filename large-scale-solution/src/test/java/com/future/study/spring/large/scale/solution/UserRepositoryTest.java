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

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Config.class})
public class UserRepositoryTest {
	private final static Logger logger = LogManager.getLogger(UserRepositoryTest.class);
	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() throws InterruptedException {
		List<Integer> sex = new ArrayList<Integer>();
		sex.add(0);
		sex.add(1);
		sex.add(2);

		Date date1 = new Date();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int j = 0; j < 100 ; j++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Random random = new Random();
					int size = sex.size();
					for (int i = 0; i <10000; i++) {
						User user = new User();
						user.setCreateTime(new Date());
						user.setLoginname("ln" + RandomUtils.getCharacterAndNumber(20));
						user.setNickname("nn" + RandomUtils.getCharacterAndNumber(20));
						user.setPassword(RandomUtils.getCharacterAndNumber(20));
						user.setPhone("135" + RandomUtils.getCharacterAndNumber(20));
						user.setEmail("gm"+RandomUtils.getCharacterAndNumber(20)+"@gmail.com");
						int index = random.nextInt(size);
						user.setSex(sex.get(index));
						userRepository.save(user);
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
						String url = "jdbc:mysql://192.168.1.246:3306/backend";
						String username = "root";
						String password = "aa112233";
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
}
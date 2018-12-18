package com.future.study.spring.large.scale.solution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Config.class})
public class UserRepositoryTest {
	private final static Logger logger = LogManager.getLogger(UserRepositoryTest.class);
	@Autowired
	private UserRepository userRepository;

	/**
	 * 使用jpa插入数据很慢，原因是jpa代码慢
	 * @throws InterruptedException
	 */
	@Test
	public void test() throws InterruptedException {
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
						User user = new User();
						user.setId(i);
						user.setCreateTime(date);
						user.setLoginname(loginname);
						user.setNickname(nickname);
						user.setPassword(randomString);
						user.setPhone(phone);
						user.setEmail(email);
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
}
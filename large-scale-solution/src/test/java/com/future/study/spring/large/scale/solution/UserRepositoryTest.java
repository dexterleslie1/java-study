package com.future.study.spring.large.scale.solution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() throws InterruptedException {
		List<Integer> sex = new ArrayList<Integer>();
		sex.add(0);
		sex.add(1);
		sex.add(2);

		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int j = 0; j < 100 ; j++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					Random random = new Random();
					int size = sex.size();
					for (int i = 0; i < 2000000; i++) {
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
	}
}
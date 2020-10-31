package com.future.demo.spring.boot.transaction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class BalanceServiceTests {

	@Autowired
	BalanceRepository balanceRepository;
	@Autowired
	BalanceLogRepository balanceLogRepository;
	@Autowired
	BalanceService balanceService;

	/**
	 *
	 */
	@Test
	public void test() throws Exception {
		// 删除balance、balance_log表数据
		this.balanceRepository.deleteAll();
		this.balanceLogRepository.deleteAll();

		// balance表新增一条数据
		double amountInit = 10000;
		Balance balance = new Balance();
		balance.setAmount(amountInit);
		this.balanceRepository.save(balance);

		// recharge成功
		double amountOriginal = this.balanceRepository.findAll().get(0).getAmount();
		double amount = 11;
		this.balanceService.recharge(amount);
		double amountCurrent = this.balanceRepository.findAll().get(0).getAmount();
		Assert.assertEquals(amountOriginal + amount, amountCurrent, 0);
		Assert.assertEquals(1, this.balanceLogRepository.findAll().size());

		amountOriginal = this.balanceRepository.findAll().get(0).getAmount();
		try {
			// recharge失败
			amount = -12;
			this.balanceService.recharge(amount);
		} catch (Exception ex) {
			//
		}
		amountCurrent = this.balanceRepository.findAll().get(0).getAmount();
		Assert.assertEquals(amountOriginal, amountCurrent, 0);
		Assert.assertEquals(1, this.balanceLogRepository.findAll().size());
	}
}
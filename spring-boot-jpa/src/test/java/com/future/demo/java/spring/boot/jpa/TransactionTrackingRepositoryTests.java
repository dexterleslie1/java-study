package com.future.demo.java.spring.boot.jpa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class TransactionTrackingRepositoryTests {
	private final static Logger logger = LogManager.getLogger(TransactionTrackingRepositoryTests.class);
	@Autowired
	private TransactionTrackingRepository transactionTrackingRepository;

	/**
	 *
	 */
	@Test
	public void test() {
		// 删除所有数据
		this.transactionTrackingRepository.deleteAll();

		TransactionTrackingModel model = new TransactionTrackingModel();
		String trackingId = UUID.randomUUID().toString();
		String payload = UUID.randomUUID().toString();
		model.setTrackingId(trackingId);
		model.setCreateTime(new Date());
		model.setPayload(payload);
		model.setStatus(TransactionStatus.PENDING);
		model.setType(TransactionTrackingType.UserBalanceRecharge);
		this.transactionTrackingRepository.save(model);

		int totalCharge = 5;
		for(int i=0; i<totalCharge; i++) {
			String trackingIdTemporary = UUID.randomUUID().toString();
			String payloadTemporary = UUID.randomUUID().toString();
			model.setTrackingId(trackingIdTemporary);
			model.setCreateTime(new Date());
			model.setPayload(payloadTemporary);
			model.setStatus(TransactionStatus.PENDING);
			model.setType(TransactionTrackingType.UserBalanceCharge);
			this.transactionTrackingRepository.save(model);
		}

		model = this.transactionTrackingRepository.findByTrackingId(trackingId);
		Assert.assertNotNull(model);
		Assert.assertEquals(payload, model.getPayload());

		List<TransactionTrackingModel> modelList =
				this.transactionTrackingRepository.findByTypeAndStatus(
						TransactionTrackingType.UserBalanceRecharge,
						TransactionStatus.PENDING);
		Assert.assertEquals(1, modelList.size());

		model.setStatus(TransactionStatus.SUCCEED);
		this.transactionTrackingRepository.save(model);
		model = this.transactionTrackingRepository.findByTrackingId(trackingId);
		Assert.assertEquals(TransactionStatus.SUCCEED, model.getStatus());

		this.transactionTrackingRepository.delete(model);
		model = this.transactionTrackingRepository.findByTrackingId(trackingId);
		Assert.assertNull(model);

		int count = this.transactionTrackingRepository.countByTypeAndStatus(
				TransactionTrackingType.UserBalanceCharge,
				TransactionStatus.PENDING);
		Assert.assertEquals(totalCharge, count);
	}
}
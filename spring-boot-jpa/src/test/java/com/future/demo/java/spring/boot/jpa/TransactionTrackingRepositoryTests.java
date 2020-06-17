package com.future.demo.java.spring.boot.jpa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Test
	public void testPagination() {
		// 删除所有数据
		this.transactionTrackingRepository.deleteAll();

		int totalRecordPending = 27;

		for(int i=0; i<totalRecordPending; i++) {
			TransactionTrackingModel model = new TransactionTrackingModel();
			String trackingId = UUID.randomUUID().toString();
			String payload = UUID.randomUUID().toString();
			model.setTrackingId(trackingId);
			model.setCreateTime(new Date());
			model.setPayload(payload);
			model.setStatus(TransactionStatus.PENDING);
			model.setType(TransactionTrackingType.UserBalanceRecharge);
			this.transactionTrackingRepository.save(model);
		}

		// 第一、二页
		for(int page=1; page<=2; page++) {
			int pageSize = 10;
			Pageable pageable = PageRequest.of(page - 1, pageSize);
			Page<TransactionTrackingModel> pagePending =
					this.transactionTrackingRepository.findByStatus(TransactionStatus.PENDING, pageable);
			// 当前页码
			Assert.assertEquals(page - 1, pagePending.getNumber());
			// 每页记录数
			Assert.assertEquals(pageSize, pagePending.getSize());
			// 当前页记录数
			Assert.assertEquals(pageSize, pagePending.getNumberOfElements());
			// 总记录数
			Assert.assertEquals(totalRecordPending, pagePending.getTotalElements());
			// 总页数
			int totalPage = totalRecordPending % pageSize == 0 ? (totalRecordPending / pageSize) : ((totalRecordPending + pageSize) / pageSize);
			Assert.assertEquals(totalPage, pagePending.getTotalPages());
		}

		// 第三页
		int pageSize = 10;
		int page = 3;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<TransactionTrackingModel> pagePending =
				this.transactionTrackingRepository.findByStatus(TransactionStatus.PENDING, pageable);
		// 当前页码
		Assert.assertEquals(page - 1, pagePending.getNumber());
		// 每页记录数
		Assert.assertEquals(pageSize, pagePending.getSize());
		// 当前页记录数
		Assert.assertEquals(totalRecordPending-2*pageSize, pagePending.getNumberOfElements());
		// 总记录数
		Assert.assertEquals(totalRecordPending, pagePending.getTotalElements());
		// 总页数
		int totalPage = totalRecordPending % pageSize == 0 ? (totalRecordPending / pageSize) : ((totalRecordPending + pageSize) / pageSize);
		Assert.assertEquals(totalPage, pagePending.getTotalPages());
	}
}
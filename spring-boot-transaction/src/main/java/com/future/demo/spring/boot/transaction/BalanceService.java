package com.future.demo.spring.boot.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 */
@Service
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    BalanceLogRepository balanceLogRepository;

    /**
     *
     * @param amount
     */
    @Transactional(rollbackFor = Throwable.class)
    public void recharge(double amount) throws Exception {
        Balance balance = this.balanceRepository.findAll().get(0);
        balance.setAmount(balance.getAmount() + amount);
        this.balanceRepository.save(balance);

        BalanceLog balanceLog = new BalanceLog();
        balanceLog.setAmount(amount);
        balanceLog.setCreateTime(new Date());
        this.balanceLogRepository.save(balanceLog);

        if(amount<0) {
            throw new BusinessException("金额小于0");
        }
    }
}

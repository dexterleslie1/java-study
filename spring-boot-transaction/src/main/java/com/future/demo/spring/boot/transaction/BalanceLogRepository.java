package com.future.demo.spring.boot.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceLogRepository extends JpaRepository<BalanceLog, Long> {
}

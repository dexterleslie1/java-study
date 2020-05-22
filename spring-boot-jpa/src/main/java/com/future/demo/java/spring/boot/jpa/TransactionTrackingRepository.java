package com.future.demo.java.spring.boot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dexterleslie@gmail.com
 */
public interface TransactionTrackingRepository extends JpaRepository<TransactionTrackingModel, Long> {
    /**
     *
     * @param trackingId
     * @return
     */
    TransactionTrackingModel findByTrackingId(String trackingId);

    /**
     *
     * @param type
     * @return
     */
    List<TransactionTrackingModel> findByTypeAndStatus(
            TransactionTrackingType type,
            TransactionStatus status);

    /**
     *
     * @param type
     * @param status
     * @return
     */
    int countByTypeAndStatus(
            TransactionTrackingType type,
            TransactionStatus status);
}

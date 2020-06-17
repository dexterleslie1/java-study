package com.future.demo.java.spring.boot.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 演示简单分页查询
     *
     * @param status
     * @param pageable
     * @return
     */
    @Query(
            value = "select model from TransactionTrackingModel model where model.status=:status order by model.id asc",
            countQuery = "select count(model.id) from TransactionTrackingModel model where model.status=:status")
    Page<TransactionTrackingModel> findByStatus(
            @Param("status") TransactionStatus status,
            Pageable pageable);
}

package com.future.demo.java.spring.boot.jpa;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dexterleslie@gmail.com
 */
@Data
@Entity
@Table(name="transaction_tracking")
public class TransactionTrackingModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -455392667421971562L;

	@Id
	private long id;
	private String trackingId;
	private TransactionTrackingType type;
	private TransactionStatus status;
	private String cause;
	private String payload;
	private Date createTime;
}

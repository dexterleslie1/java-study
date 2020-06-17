package com.future.demo.java.spring.boot.jpa;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

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
	@Enumerated(EnumType.STRING)
	private TransactionTrackingType type;
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	private String cause;
	private String payload;
	private Date createTime;
}

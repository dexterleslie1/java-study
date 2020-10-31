package com.future.demo.spring.boot.transaction;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "balance_log")
@Getter
@Setter
public class BalanceLog {
    @Id
    private long id;
    private double amount;
    private Date createTime;
}

package com.future.demo.txlcn;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 */
@Data
@Entity
@Table(name = "t_demo")
public class Demo {
    @Id
    private long id;
    private String data;
    private Date createTime;
}

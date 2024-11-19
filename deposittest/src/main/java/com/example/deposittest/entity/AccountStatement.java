package com.example.deposittest.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "account_statement")
public class AccountStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer accountId;
    private Integer orderId;
    private BigDecimal amount;
    private Integer serialId;
    private String remark;
    private BigDecimal closingBalance;

}

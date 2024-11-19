package com.example.deposittest.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "order_withdrawal")
public class OrderWithdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer userId;
    private BigDecimal amount;
    private Integer version; // for optimistic locking


}

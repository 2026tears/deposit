package com.example.deposittest.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "account")
    public class Account {
        @Id
        private Integer id;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private BigDecimal balance;

        private Integer serialId;


    }


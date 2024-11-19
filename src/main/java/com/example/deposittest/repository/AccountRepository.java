package com.example.deposittest.repository;


import com.example.deposittest.entity.Account;
import com.example.deposittest.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Modifying
    @Query("UPDATE Account a SET a.balance = :newBalance, a.version = a.version + 1 WHERE a.id = :accountId AND a.version = :currentVersion")
    int updateBalance(@Param("accountId") Integer accountId, @Param("newBalance") BigDecimal newBalance, @Param("currentVersion") int currentVersion);
}

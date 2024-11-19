package com.example.deposittest.repository;


import com.example.deposittest.entity.OrderWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface OrderWithdrawalRepository extends JpaRepository<OrderWithdrawal, Long> {
    OrderWithdrawal findByUserIdAndAmountAndVersion(Integer userId, BigDecimal amount, Integer version);

    @Modifying
    @Transactional
    @Query("UPDATE OrderWithdrawal o SET o.version = :version, o.updateTime = :updateTime WHERE o.id = :id")
    void updateVersionAndUpdateTimeById(@Param("id") Integer id, @Param("version") Integer version, @Param("updateTime") LocalDateTime updateTime);
}

package com.example.deposittest.service;


import com.example.deposittest.entity.Account;
import com.example.deposittest.entity.AccountStatement;
import com.example.deposittest.entity.OrderWithdrawal;
import com.example.deposittest.repository.AccountRepository;
import com.example.deposittest.repository.AccountStatementRepository;
import com.example.deposittest.repository.OrderWithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WithdrawalService {

    //使用 @Autowired 注解将 AccountRepository, AccountStatementRepository, 和 OrderWithdrawalRepository 注入到服务类中，分别用于账户、账户流水以及订单记录。
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountStatementRepository accountStatementRepository;

    @Autowired
    private OrderWithdrawalRepository orderWithdrawalRepository;


    //@Transactional 注解开启事务管理，注解用于声明式事务管理，确保数据的一致性和可靠性
    //定义 withdraw 方法，接收accountId和amount，返回取款结果的字符串。
    @Transactional
    public String withdraw(int accountId, BigDecimal amount) {
        //增加订单记录 创建一个AccountStatement实例，用于记录取款操作
        AccountStatement order = new AccountStatement();
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setAccountId(accountId);
        order.setAmount(amount);
        order.setSerialId(UUID.randomUUID().toString().hashCode()); //序列号有问题的（有正有负） 之前用的System.currentTimeMillis()会出现重复值
        accountStatementRepository.save(order);//保存记录到数据库

        // 模拟对外请求
        try {
            Thread.sleep(1000);//停顿1秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error during sleep"; //异常处理
        }

        //通过findById查询账户信息
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        if (account.getBalance().compareTo(amount) >= 0) {
            BigDecimal newBalance = account.getBalance().subtract(amount); ////检查账户余额

            //更新账户余额
            int updatedCount = accountRepository.updateBalance(accountId, newBalance, account.getSerialId()); //updateBalance方法会根据SerialId检测版本号一致性
            if (updatedCount == 0) {
                return "失败：检测到并发";
            }

            //记录取款交易并将其保存到数据库
            OrderWithdrawal transaction = new OrderWithdrawal();
            transaction.setCreateTime(LocalDateTime.now());
            transaction.setUpdateTime(LocalDateTime.now());
            transaction.setUserId(accountId);
            transaction.setAmount(amount);
            orderWithdrawalRepository.save(transaction);
            return "成功";
        } else {
            return "资金不足";
        }
            }
    }

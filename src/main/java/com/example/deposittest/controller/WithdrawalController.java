package com.example.deposittest.controller;

import com.example.deposittest.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Integer accountId, @RequestParam BigDecimal amount) {
        return withdrawalService.withdraw(accountId, amount);
    }
}
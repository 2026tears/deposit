package com.example.deposittest.test;

import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.web.util.UriComponentsBuilder;

public class ConcurrentWithdrawalTest {
    public static void main(String[] args) {
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(10); //允许并发

        //模拟提现
        final int accountId = 1;
        final BigDecimal withdrawalAmount = new BigDecimal("100.00"); // 每次提现的金额
        final String baseUrl = "http://localhost:6000/api/withdraw";

        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 100; i++) { // 模拟 100 次请求
            executor.submit(() -> {
                try {
                    //URL
                    String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                            .queryParam("accountId", accountId)
                            .queryParam("amount", withdrawalAmount)
                            .toUriString();

                    //发送请求
                    String response = restTemplate.postForObject(url, null, String.class);

                    System.out.println("Response: " + response);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            });
        }

        executor.shutdown();

    }
}

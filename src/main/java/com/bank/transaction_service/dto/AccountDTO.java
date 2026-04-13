package com.bank.transaction_service.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private String customerName;
    private Double balance;
}

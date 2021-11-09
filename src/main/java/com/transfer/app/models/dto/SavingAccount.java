package com.transfer.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class SavingAccount implements BankAccount{
    private String id;
    private Customer customer;
    private String cardNumber;
    private Integer limitTransactions;
    private Integer freeTransactions;
    private Double commissionTransactions;
    private Double balance;
    private Double minAverageVip;
    private LocalDateTime createAt;
    private List<Managers> owners;
    private List<Managers> signatories;
}

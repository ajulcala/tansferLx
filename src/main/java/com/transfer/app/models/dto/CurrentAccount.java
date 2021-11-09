package com.transfer.app.models.dto;

import lombok.Data;

import java.time.LocalDateTime;

import java.util.List;
@Data
public class CurrentAccount implements BankAccount{
    private String id;
    private Customer customer;
    private String cardNumber;
    private Integer freeTransactions;
    private Double commissionTransactions;
    private Double commissionMaintenance;
    private Double balance;
    private LocalDateTime createAt;
    private List<Managers> owners;
    private List<Managers> signatories;
}

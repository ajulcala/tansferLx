package com.transfer.app.services;

import com.transfer.app.models.documents.Transfer;
import com.transfer.app.models.dto.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface TransferService {
    Mono<Transfer> create(Transfer t);
    Flux<Transfer> findAll();
    Mono<Transfer> findById(String id);
    Mono<Transfer> update(Transfer t);
    Mono<Boolean> delete(String t);
    Mono<Optional<BankAccount>> findBankAccount(String cardNumber);
    Mono<BankAccount> updateBankAccountBalance(BankAccount numberAccount);
}

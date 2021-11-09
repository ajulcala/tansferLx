package com.transfer.app.services.impl;

import com.transfer.app.models.dao.TransferDao;
import com.transfer.app.models.documents.Transfer;
import com.transfer.app.models.dto.BankAccount;
import com.transfer.app.models.dto.CurrentAccount;
import com.transfer.app.models.dto.FixedTerm;
import com.transfer.app.models.dto.SavingAccount;
import com.transfer.app.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
@Service
public class TransferServiceImpl implements TransferService {

    @Value("${config.base.current}")
    private String urlc;
    @Value("${config.base.fixed}")
    private String urlf;
    @Value("${config.base.saving}")
    private String urls;

    WebClient webClientCurrent = WebClient.create(urlc);

    WebClient webClientFixed = WebClient.create(urlf);

    WebClient webClientSaving = WebClient.create(urls);

    @Autowired
    TransferDao dao;

    @Override
    public Mono<Transfer> create(Transfer t) {
        return dao.save(t);
    }

    @Override
    public Flux<Transfer> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<Transfer> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Transfer> update(Transfer t) {
        return dao.save(t);
    }

    @Override
    public Mono<Boolean> delete(String t) {
        return dao.findById(t)
                .flatMap(cf -> dao.delete(cf).then(Mono.just(Boolean.TRUE))
                ).defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<Optional<BankAccount>> findBankAccount(String cardNumber) {
        return webClientCurrent.get().uri("/findByAccountNumber/{id}", cardNumber)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CurrentAccount.class)
                .map(currentAccount -> {
                    System.out.println("Encontro currentAccount > " + currentAccount.getId());
                    return Optional.of((BankAccount)currentAccount);})
                .switchIfEmpty(webClientFixed.get().uri("/findByAccountNumber/{id}", cardNumber)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(FixedTerm.class)
                        .map(fixedTerm -> {
                            System.out.println("Encontro fixedTerm > " + fixedTerm.getId());
                            return Optional.of((BankAccount)fixedTerm);
                        })
                        .switchIfEmpty(webClientSaving.get().uri("/findByAccountNumber/{id}", cardNumber)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(SavingAccount.class)
                                .map(savingAccount -> {
                                    System.out.println("Encontro savingAccount > " + savingAccount.getId());
                                    return Optional.of((BankAccount)savingAccount);
                                }))
                        .defaultIfEmpty(Optional.empty())
                );
    }

    @Override
    public Mono<BankAccount> updateBankAccountBalance(BankAccount account) {
        if(account instanceof CurrentAccount){
            return webClientCurrent.put().uri("/updateTransference")
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue((CurrentAccount)account)
                    .retrieve()
                    .bodyToMono(CurrentAccount.class)
                    .map(ca -> (BankAccount)ca);
        }
        if(account instanceof FixedTerm){
            return webClientFixed.put().uri("/updateTransference")
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue((FixedTerm)account)
                    .retrieve()
                    .bodyToMono(FixedTerm.class)
                    .map(ft -> (BankAccount)ft);
        }
        if(account instanceof SavingAccount){
            return webClientSaving.put().uri("/updateTransference")
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue((SavingAccount)account)
                    .retrieve()
                    .bodyToMono(SavingAccount.class)
                    .map(ft -> (BankAccount)ft);
        }
        return Mono.empty();
    }
}

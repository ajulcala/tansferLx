package com.transfer.app.models.dao;

import com.transfer.app.models.documents.Transfer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransferDao extends ReactiveMongoRepository<Transfer, String> {
}

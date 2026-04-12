package dev.droca.desafio_itau.db;

import java.util.*;

import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.model.TransactionFakeEntity;
import org.springframework.stereotype.Component;

@Component
public class Db {
    private List<TransactionFakeEntity> database = new ArrayList<>();

    private int id = 0;

    public TransactionFakeEntity save(TransactionRequestDTO transactionRequest) {
        TransactionFakeEntity transactionFakeEntity = new TransactionFakeEntity(id++, transactionRequest);
        database.add(transactionFakeEntity);
        return transactionFakeEntity;
    }

    public void delete() {
        database.clear();
    }

    public List<TransactionFakeEntity> getDatabase() {
        return database;
    }
}
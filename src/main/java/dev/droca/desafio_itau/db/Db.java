package dev.droca.desafio_itau.db;

import java.util.*;

import dev.droca.desafio_itau.dto.TransactionRequestDTO;


public class Db {
    private Map<Integer, TransactionRequestDTO> database = new HashMap<>();

    private int id = 0;

    public void save(TransactionRequestDTO transactionRequest) {
        database.put(id++, transactionRequest);
        id++;
    }

    public void delete() {
        database.clear();
    }

    public List<TransactionRequestDTO> getDatabase() {
        return new ArrayList<>(database.values());
    }

}
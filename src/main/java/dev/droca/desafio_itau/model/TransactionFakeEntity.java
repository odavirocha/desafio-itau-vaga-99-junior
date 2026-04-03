package dev.droca.desafio_itau.model;

import dev.droca.desafio_itau.dto.TransactionRequestDTO;

public record TransactionFakeEntity(
    Integer id,
    TransactionRequestDTO transactionRequest
) {}

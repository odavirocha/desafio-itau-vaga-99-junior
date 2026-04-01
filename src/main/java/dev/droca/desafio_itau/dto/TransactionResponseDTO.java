package dev.droca.desafio_itau.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResponseDTO(
    BigDecimal valor,
    OffsetDateTime dataHora
) {}

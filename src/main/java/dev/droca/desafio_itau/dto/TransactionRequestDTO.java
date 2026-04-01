package dev.droca.desafio_itau.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

public record TransactionRequestDTO(
    @NotNull(message = "Campo [valor] é obrigatório.")
    @PositiveOrZero(message = "O valor tem que ser positivo e maior ou igual a zero.")
    BigDecimal valor,
    @NotNull(message = "Campo [dataHora] é obrigatório.")
    @Past(message = "A data está no futuro.")
    OffsetDateTime dataHora
) {}

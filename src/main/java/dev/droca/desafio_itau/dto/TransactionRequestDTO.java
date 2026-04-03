package dev.droca.desafio_itau.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

public record TransactionRequestDTO(
    @NotNull(message = "Campo [valor] é obrigatório.")
    @PositiveOrZero(message = "O valor tem que ser positivo e maior ou igual a zero.")
    @Schema(description = "Valor da transação", example = "25.98")
    BigDecimal valor,
    @NotNull(message = "Campo [dataHora] é obrigatório.")
    @Past(message = "A data está no futuro.")
    @Schema(description = "Data e hora da transação", example = "2026-04-01T14:58:10-03:00")
    OffsetDateTime dataHora
) {}

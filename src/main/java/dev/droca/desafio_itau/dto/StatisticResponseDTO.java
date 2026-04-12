package dev.droca.desafio_itau.dto;

public record StatisticResponseDTO(
    Integer count,
    Double sum,
    Double avg,
    Double min,
    Double max
) {}

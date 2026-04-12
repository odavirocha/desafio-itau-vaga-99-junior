package dev.droca.desafio_itau.service;

import dev.droca.desafio_itau.db.Db;
import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.dto.TransactionResponseDTO;
import dev.droca.desafio_itau.model.TransactionFakeEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class DesafioServiceTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @InjectMocks
    DesafioService desafioService;

    @Mock
    Db db;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Deve retornar sucesso ao criar uma transação")
    void createTransactionSuccessTest() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(25.98), OffsetDateTime.now());
        desafioService.createTransaction(transactionRequest);

        verify(db, times(1)).save(transactionRequest);
    }

    @Test
    @DisplayName("Deve retornar sucesso ao enviar um valor nulo")
    void createTransactionValidateNotNullValorTest() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(null, OffsetDateTime.now().minusSeconds(10));

        Set<ConstraintViolation<TransactionRequestDTO>> violations = validator.validate(transactionRequest);

        String error = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        assertEquals("Campo [valor] é obrigatório.", error);
    }

    @Test
    @DisplayName("Deve retornar sucesso ao enviar valor negativo no atributo valor")
    void createTransactionValidatePositiveOrZeroValorTest() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(-1), OffsetDateTime.now().minusSeconds(10));

        Set<ConstraintViolation<TransactionRequestDTO>> violations = validator.validate(transactionRequest);

        String error = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        assertEquals("O valor tem que ser positivo e maior ou igual a zero.", error);
    }

    @Test
    @DisplayName("Deve retornar sucesso ao enviar uma data nula")
    void createTransactionValidateNotNullDataHoraTest() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(20), null);

        Set<ConstraintViolation<TransactionRequestDTO>> violations = validator.validate(transactionRequest);

        String error = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        assertEquals("Campo [dataHora] é obrigatório.", error);
    }

    @Test
    @DisplayName("Deve retornar sucesso ao enviar uma data no futuro")
    void createTransactionValidatePastDataHoraTest() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(20), OffsetDateTime.now().plusSeconds(10));

        Set<ConstraintViolation<TransactionRequestDTO>> violations = validator.validate(transactionRequest);

        String error = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        assertEquals("A data está no futuro.", error);
    }

    @Test
    @DisplayName("Deve deletar todas as transações")
    void deleteTransactionSuccessTest() {
        desafioService.deleteTransaction();
        verify(db, times(1)).delete();
    }

    @Test
    @DisplayName("Deve retornar todas as estatísticas com range padrão")
    void getStatisticSuccessWithoutRangeTest() {
        List<TransactionFakeEntity> databaseTransactions = List.of(
            new TransactionFakeEntity(2, new TransactionRequestDTO(BigDecimal.valueOf(78.56), OffsetDateTime.now().minusSeconds(120))),
            new TransactionFakeEntity(3, new TransactionRequestDTO(BigDecimal.valueOf(12.98), OffsetDateTime.now().minusSeconds(20))),
            new TransactionFakeEntity(4, new TransactionRequestDTO(BigDecimal.valueOf(126.73), OffsetDateTime.now().minusSeconds(15))),
            new TransactionFakeEntity(5, new TransactionRequestDTO(BigDecimal.valueOf(4.99), OffsetDateTime.now().minusSeconds(10))),
            new TransactionFakeEntity(1, new TransactionRequestDTO(BigDecimal.valueOf(25.98), OffsetDateTime.now()))
        );

        Integer range = null;

        when(db.getDatabase()).thenReturn(databaseTransactions);

        StatisticResponseDTO response = desafioService.getStatistic(range);

        assertEquals(4, response.count());
        assertEquals(170.68, response.sum());
        assertEquals(42.67, response.avg());
        assertEquals(4.99, response.min());
        assertEquals(126.73, response.max());
    }

    @Test
    @DisplayName("Deve retornar todas as estatísticas com range modificado")
    void getStatisticSuccessWithRangeTest() {
        List<TransactionFakeEntity> databaseTransactions = List.of(
            new TransactionFakeEntity(2, new TransactionRequestDTO(BigDecimal.valueOf(78.56), OffsetDateTime.now().minusSeconds(125))),
            new TransactionFakeEntity(3, new TransactionRequestDTO(BigDecimal.valueOf(12.98), OffsetDateTime.now().minusSeconds(120))),
            new TransactionFakeEntity(4, new TransactionRequestDTO(BigDecimal.valueOf(126.73), OffsetDateTime.now().minusSeconds(15))),
            new TransactionFakeEntity(5, new TransactionRequestDTO(BigDecimal.valueOf(4.99), OffsetDateTime.now().minusSeconds(10))),
            new TransactionFakeEntity(1, new TransactionRequestDTO(BigDecimal.valueOf(25.98), OffsetDateTime.now()))
        );


        Integer range = 120;
        when(db.getDatabase()).thenReturn(databaseTransactions);

        StatisticResponseDTO response = desafioService.getStatistic(range);

        assertEquals(4, response.count());
        assertEquals(170.68, response.sum());
        assertEquals(42.67, response.avg());
        assertEquals(4.99, response.min());
        assertEquals(126.73, response.max());
    }

    @Test
    @DisplayName("Deve retornar todas as estatísticas zeradas")
    void getStatisticSuccessWithNoTransactionsTest() {
        List<TransactionFakeEntity> databaseTransactions = List.of(
            new TransactionFakeEntity(2, new TransactionRequestDTO(BigDecimal.valueOf(78.56), OffsetDateTime.now().minusSeconds(120))),
            new TransactionFakeEntity(3, new TransactionRequestDTO(BigDecimal.valueOf(12.98), OffsetDateTime.now().minusSeconds(61))),
            new TransactionFakeEntity(4, new TransactionRequestDTO(BigDecimal.valueOf(126.73), OffsetDateTime.now().minusSeconds(61))),
            new TransactionFakeEntity(5, new TransactionRequestDTO(BigDecimal.valueOf(4.99), OffsetDateTime.now().minusSeconds(61))),
            new TransactionFakeEntity(1, new TransactionRequestDTO(BigDecimal.valueOf(25.98), OffsetDateTime.now().minusSeconds(61)))
        );

        int range = 60; // padrão é 60, mas pode ser 120, 130...

        when(db.getDatabase()).thenReturn(databaseTransactions);

        StatisticResponseDTO response = desafioService.getStatistic(range);

        assertEquals(0, response.count());
        assertEquals(0.0, response.sum());
        assertEquals(0.0, response.avg());
        assertEquals(0.0, response.min());
        assertEquals(0.0, response.max());
    }


}

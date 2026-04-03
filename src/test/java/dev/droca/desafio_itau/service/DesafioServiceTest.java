package dev.droca.desafio_itau.service;

import dev.droca.desafio_itau.db.Db;
import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.dto.TransactionResponseDTO;
import dev.droca.desafio_itau.model.TransactionFakeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DesafioServiceTest {

    @InjectMocks
    DesafioService desafioService;

    @Mock
    Db db;

    @Test
    @DisplayName("Deve retornar sucesso ao criar uma transação")
    void createTransactionSuccessTest() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(25.98), OffsetDateTime.now());
        TransactionFakeEntity transactionResponse = new TransactionFakeEntity(1, transactionRequest);

        when(db.save(transactionRequest)).thenReturn(transactionResponse);

        TransactionResponseDTO response = desafioService.createTransaction(transactionRequest);

        assertEquals(transactionRequest.valor(), response.valor());
        assertEquals(transactionRequest.dataHora(), response.dataHora());
    }

    @Test
    @DisplayName("Deve deletar todas as transações")
    void deleteTransactionSuccessTest() {
        desafioService.deleteTransaction();
        verify(db, times(1)).delete();
    }

    @Test
    @DisplayName("Deve retornar todas as estatísticas")
    void getStatisticSuccessTest() {
        List<TransactionFakeEntity> databaseTransactions = List.of(
            new TransactionFakeEntity(2, new TransactionRequestDTO(BigDecimal.valueOf(78.56), OffsetDateTime.now().minusSeconds(120))),
            new TransactionFakeEntity(3, new TransactionRequestDTO(BigDecimal.valueOf(12.98), OffsetDateTime.now().minusSeconds(20))),
            new TransactionFakeEntity(4, new TransactionRequestDTO(BigDecimal.valueOf(126.73), OffsetDateTime.now().minusSeconds(15))),
            new TransactionFakeEntity(5, new TransactionRequestDTO(BigDecimal.valueOf(4.99), OffsetDateTime.now().minusSeconds(10))),
            new TransactionFakeEntity(1, new TransactionRequestDTO(BigDecimal.valueOf(25.98), OffsetDateTime.now()))
        );

        int range = 60; // padrão é 60, mas pode ser 120, 130...

        when(db.getDatabase()).thenReturn(databaseTransactions);

        StatisticResponseDTO response = desafioService.getStatistic(range);

        assertEquals(4, response.count());
        assertEquals(170.68, response.sum());
        assertEquals(42.67, response.avg());
        assertEquals(4.99, response.min());
        assertEquals(126.73, response.max());
    }

}

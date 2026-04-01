package dev.droca.desafio_itau.service;

import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import dev.droca.desafio_itau.db.Db;
import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.dto.TransactionResponseDTO;

@Service
@Slf4j
public class DesafioService {

    Db db = new Db();

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequest) {
        long processStart = System.nanoTime();
        TransactionRequestDTO a = new TransactionRequestDTO(transactionRequest.valor(), transactionRequest.dataHora());
        db.save(a);
        log.info("Transação criada. {}", OffsetDateTime.now());

        long processEnd = System.nanoTime();
        log.info("Tempo de execução do método deleteTransaction() foi de: "+ (((double)processEnd - processStart) / 1000000)+ "ms");
        return new TransactionResponseDTO(a.valor().setScale(2, RoundingMode.DOWN), a.dataHora());
    }

    public void deleteTransaction() {
        long processStart = System.nanoTime();
        db.delete();
        log.info("Transação deletada.");

        long processEnd = System.nanoTime();
        log.info("Tempo de execução do método deleteTransaction() foi de: "+ (((double)processEnd - processStart) / 1000000)+ "ms");
    }
    
    public StatisticResponseDTO getStatistic(int range) {
        long processStart = System.nanoTime();
        OffsetDateTime limitDate = OffsetDateTime.now().minusSeconds(range);
        List<TransactionRequestDTO> transactions = db.getDatabase();
        Set<TransactionRequestDTO> betweenValues = new HashSet<>(); // dentro dos 60s
        DoubleSummaryStatistics statistic = new DoubleSummaryStatistics();

        for (TransactionRequestDTO transaction : transactions) {
            boolean verifyTime = transaction.dataHora().toEpochSecond() >= limitDate.toEpochSecond();
            if (verifyTime) {
                betweenValues.add(transaction);
                statistic.accept(Double.parseDouble(String.valueOf(transaction.valor().setScale(2, RoundingMode.DOWN))));
            }
        }

        int count = betweenValues.size();

        if (count == 0) {
            long processEnd = System.nanoTime();
            log.info("Tempo de execução do método getStatistic() foi de: "+ (((double)processEnd - processStart) / 1000000)+ "ms");
            return new StatisticResponseDTO(0, 0.0, 0.0, 0.0, 0.0);
        }

        long processEnd = System.nanoTime();
        log.info("Tempo de execução do método getStatistic() foi de: "+ (((double)processEnd - processStart) / 1000000)+ "ms");
        return new StatisticResponseDTO(count, statistic.getSum(), statistic.getAverage(), statistic.getMin(), statistic.getMax());
    }

}

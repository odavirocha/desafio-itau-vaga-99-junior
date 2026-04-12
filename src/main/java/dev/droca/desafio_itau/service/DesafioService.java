package dev.droca.desafio_itau.service;

import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;

import dev.droca.desafio_itau.model.TransactionFakeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import dev.droca.desafio_itau.db.Db;
import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.dto.TransactionResponseDTO;

@Service
@Slf4j
public class DesafioService {

    Db db;

    public DesafioService() {}

    public DesafioService(Db db) {
        this.db = db;
    }

    public void createTransaction(TransactionRequestDTO transactionRequest) {
        long processStart = System.nanoTime();
        db.save(transactionRequest);
        log.info("Transação criada. {}", OffsetDateTime.now());

        long processEnd = System.nanoTime();
        log.info("Tempo de execução do método deleteTransaction() foi de: "+ (((double)processEnd - processStart) / 1000000)+ "ms");
    }

    public void deleteTransaction() {
        long processStart = System.nanoTime();
        db.delete();
        log.info("Transação deletada.");

        long processEnd = System.nanoTime();
        log.info("Tempo de execução do método deleteTransaction() foi de: "+ (((double)processEnd - processStart) / 1000000)+ "ms");
    }
    
    public StatisticResponseDTO getStatistic(Integer range) {
        long processStart = System.nanoTime();
        int trueRange = range != null ? range : 60;
        OffsetDateTime limitDate = OffsetDateTime.now().minusSeconds(trueRange);
        List<TransactionFakeEntity> transactions = db.getDatabase();
        Set<TransactionRequestDTO> betweenValues = new HashSet<>(); // dentro dos 60s
        DoubleSummaryStatistics statistic = new DoubleSummaryStatistics();

        for (TransactionFakeEntity value : transactions) {
            TransactionRequestDTO transaction = value.transactionRequest();
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

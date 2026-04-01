package dev.droca.desafio_itau.service;

import java.time.OffsetDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

import dev.droca.desafio_itau.db.Db;
import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.dto.TransactionResponseDTO;

@Service
public class DesafioService {

    Db db = new Db();

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequest) {
        TransactionRequestDTO a = new TransactionRequestDTO(transactionRequest.valor(), OffsetDateTime.now());
        db.save(a);
        return new TransactionResponseDTO(a.valor(), a.dataHora());
    }

    public void deleteTransaction() {
        db.delete();
    }
    
    public StatisticResponseDTO getStatistic() {
        OffsetDateTime limitDate = OffsetDateTime.now().minusSeconds(60);
        List<TransactionRequestDTO> transactions = db.getDatabase();
        Set<TransactionRequestDTO> betweenValues = new HashSet<>(); // dentro dos 60s
        DoubleSummaryStatistics statistic = new DoubleSummaryStatistics();

        for (TransactionRequestDTO transaction : transactions) {
            boolean verifyTime = transaction.dataHora().toEpochSecond() >= limitDate.toEpochSecond();
            if (verifyTime) {
                betweenValues.add(transaction);
                statistic.accept(transaction.valor());
            }
        }

        int count = betweenValues.size();

        if (count == 0) return new StatisticResponseDTO(0, 0.0, 0.0, 0.0, 0.0);

        return new StatisticResponseDTO(count, statistic.getSum(), statistic.getAverage(), statistic.getMin(), statistic.getMax());
    }

}

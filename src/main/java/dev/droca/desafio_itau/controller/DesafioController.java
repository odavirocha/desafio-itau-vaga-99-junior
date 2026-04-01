package dev.droca.desafio_itau.controller;

import dev.droca.desafio_itau.dto.TransactionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.service.DesafioService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/")
@Slf4j
public class DesafioController {
    
    DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping("transacao")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody @Valid TransactionRequestDTO transactionRequest) {
        log.info("Criando uma nova transação!");
        TransactionResponseDTO response = desafioService.createTransaction(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("transacao")
    public ResponseEntity<Void> deleteTransaction() {
        log.info("Limpado todas as transações!");
        desafioService.deleteTransaction();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({"estatistica", "estatistica/{range}"})
    public ResponseEntity<StatisticResponseDTO> getStatistic(@PathVariable(required = false) Integer range) {
        int trueRange = range != null ? range : 60;
        log.info("Retornando todas as estatísticas dos últimos " + trueRange + " segundos");
        StatisticResponseDTO response = desafioService.getStatistic(trueRange);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

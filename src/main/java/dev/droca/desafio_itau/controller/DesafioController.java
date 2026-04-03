package dev.droca.desafio_itau.controller;

import dev.droca.desafio_itau.dto.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Rota de transações", description = "Nessa rota é possível criar, deletar e receber uma visão geral de suas transações")
public class DesafioController {
    
    DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping("transacao")
    @Operation(summary = "Cria uma transaçao")
    @ApiResponse(responseCode = "201", content = @Content)
    @ApiResponse(responseCode = "422", content = @Content)
    @ApiResponse(responseCode = "400", content = @Content)
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody @Valid TransactionRequestDTO transactionRequest) {
        log.info("Criando uma nova transação!");
        TransactionResponseDTO response = desafioService.createTransaction(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("transacao")
    @Operation(summary = "Deleta todas as transações")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Void> deleteTransaction() {
        log.info("Limpado todas as transações!");
        desafioService.deleteTransaction();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({"estatistica", "estatistica/{range}"})
    @Operation(summary = "Retorna estatísticas das transações", description = "Retorna a quantidade de transações, soma, média, valor mínimo e máximo de transações")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<StatisticResponseDTO> getStatistic(@Parameter(description = "Tempo (em segundos) para calcular as estatísticas.", example = "120") @PathVariable(required = false) Integer range) {
        int trueRange = range != null ? range : 60;
        log.info("Retornando todas as estatísticas dos últimos " + trueRange + " segundos");
        StatisticResponseDTO response = desafioService.getStatistic(trueRange);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package dev.droca.desafio_itau.controller;

import dev.droca.desafio_itau.dto.StatisticResponseDTO;
import dev.droca.desafio_itau.dto.TransactionRequestDTO;
import dev.droca.desafio_itau.dto.TransactionResponseDTO;
import dev.droca.desafio_itau.service.DesafioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.Mockito.when;

@WebMvcTest(DesafioController.class)
@AutoConfigureRestTestClient
class DesafioControllerTest {

    @Autowired
    private RestTestClient rest;

    @MockitoBean
    private DesafioService desafioService;

    @Test
    @DisplayName("Deve retornar 500 quando ocorrer um erro inesperado")
    void shouldReturn500WhenUnexpectedErrorOccurs() {
        when(desafioService.getStatistic(60)).thenThrow(new RuntimeException("erro inesperado"));

        rest.get().uri("/estatistica/60")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Deve criar uma transação")
    void createTransactionSuccess() {
        TransactionRequestDTO request = new TransactionRequestDTO(BigDecimal.valueOf(22.76), OffsetDateTime.now().minusSeconds(20));

        rest.post().uri("/transacao")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .exchange()
            .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Deve retornar 422 quando a data estiver errada")
    void createTransactionReturn422WhenDateIsWrong() {
        TransactionRequestDTO request = new TransactionRequestDTO(BigDecimal.valueOf(22.76), OffsetDateTime.now().plusSeconds(20));

        rest.post().uri("/transacao")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @Test
    @DisplayName("Deve retornar 400 quando a requisição estiver mal feita")
    void createTransactionReturn400WhenBadRequest() {
        // invalidRequest o valor ta sem virgula.
        String invalidRequest = """
            {
                "valor": 22.53 
                "dataHora": "2025-01-01T00:00:00Z"
            }
        """;

        rest.post().uri("/transacao")
        .contentType(MediaType.APPLICATION_JSON)
        .body(invalidRequest)
        .exchange()
        .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Deve deletar todas as transações")
    void deleteTransactionSuccess() {
        rest.delete().uri("/transacao").exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("Deve retornar estatísticas com valores zerados quando nenhum range é informado")
    void getStatisticSuccessWithoutRange() {
        StatisticResponseDTO response = new StatisticResponseDTO(0, 0.0, 0.0, 0.0, 0.0);

        when(desafioService.getStatistic(null)).thenReturn(response);

        rest.get().uri("/estatistica").exchange()
            .expectStatus().isOk()
            .expectBody(StatisticResponseDTO.class).isEqualTo(response);
    }

    @Test
    @DisplayName("Deve retornar estatísticas com valores zerados quando o range informado é 120")
    void getStatisticSuccessWithRange() {
        StatisticResponseDTO response = new StatisticResponseDTO(0, 0.0, 0.0, 0.0, 0.0);

        when(desafioService.getStatistic(120)).thenReturn(response);

        rest.get().uri("/estatistica/120").exchange()
            .expectStatus().isOk()
            .expectBody(StatisticResponseDTO.class).isEqualTo(response);
    }

    @Test
    @DisplayName("Deve retornar 422 ao enviar range negativo na estatística")
    void getStatisticShouldReturn422WhenRangeIsNegative() {
        rest.get().uri("/estatistica/-1")
        .exchange().expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
    }
}

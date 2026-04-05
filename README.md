# Itaú Unibanco - Desafio de Programação
> Desafio proposto pelo [Rafael Lins](https://github.com/rafaellins-itau).

O desafio consiste em criar uma API REST que **recebe transações** e **retorna estatísticas** sob essas transações, seguindo a
lista de requisitos no readme do [projeto original](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/assets/stack-white.svg" height="40">
  <img alt="Stack" src="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/assets/stack-black.svg" height="40">
</picture>

<table>
  <tr align="center">
    <td>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/icons/java-white.svg" width="50">
      <img alt="Icone java" src="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/icons/java-black.svg" width="50">
    </picture>
    </td>
    <td>
      <picture>
        <source media="(prefers-color-scheme: dark)" srcset="https://cdn.simpleicons.org/spring/white" width="50">
        <img alt="Icone Spring Boot" src="https://cdn.simpleicons.org/spring/black" width="50">
      </picture>
    </td>
    <td>
      <picture>
        <source media="(prefers-color-scheme: dark)" srcset="https://cdn.simpleicons.org/apachemaven/white" width="50">
        <img alt="Icone Maven" src="https://cdn.simpleicons.org/apachemaven/black" width="50">
      </picture>
    </td>
  </tr>
  <tr>
    <td> <strong>Java 17</strong> </td>
    <td> <strong>Spring 4.0.5</strong> </td>
    <td> <strong>Maven</strong> </td>
    </tr>
</table>

---

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/assets/como-instalar-white.svg" height="40">
  <img alt="Icone java" src="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/assets/como-instalar-black.svg" height="40">
</picture>

<details>
  <summary><strong> ⚙️ Como rodar manualmente. </strong> <sub> (expandir) </sub></summary>

-  ### Pré-requisitos
    - Java 17+
    - Maven

- ### Passo a passo

  ### 1. Instale as dependências.

  Rode no terminal o comando:

    ```bash
    ./mvnw clean install
    ```
  ou
    ```bash
    mvn clean install
    ```

  ### 2. Execute o projeto.
  O projeto pode ser executado utilizando banco de dados local ou externo.

  Para usar banco de dados local rode no terminal o comando:

    ```bash
    ./mvnw spring-boot:run
    ```
  ou
    ```bash
    mvn spring-boot:run
    ```

  Isso deixará a API disponível em http://localhost:8080/.
  > ♦️ Use ferramentas como [Insmonia](https://insomnia.rest/download) ou [Postman](https://www.postman.com/) para testar a API.
</details>


<details>
  <summary><strong> 🐋 Como rodar com Docker. </strong> <sub> (expandir) </sub></summary>

- ### Pré-requisitos
    - Docker
    - Docker Compose

- ### Passo a passo

  ### 1. Escolha qual banco usar.

    - Para usar banco em nuvem.
    ```bash
    
    ```

  Isso deixará a API disponível em http://localhost:8080/.
  > ♦️ Use ferramentas como [Insmonia](https://insomnia.rest/download) ou [Postman](https://www.postman.com/) para testar a API.
</details>

---

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/assets/rotas-white.svg" height="40">
  <img alt="Icone java" src="https://raw.githubusercontent.com/oDroca/icones-para-readme/main/assets/rotas-black.svg" height="40">
</picture>

<details>
    <summary><strong> 💸 Transações </strong> <sub> (expandir) </sub></summary>

* #### POST
    * #### Como criar uma transação `/transacao`
      <details>
          <summary><strong> Exemplo de requisição 📤 </strong> <sub> (expandir) </sub></summary>

        ```json
            {
                "valor": 60.31,
                "dataHora": "2026-04-01T14:58:10-03:00"
            }
        ```
      </details>


* #### DELETE
  * #### Como deletar transações `/transacao`
    > **AVISO:** *Essa rota deleta **todas** as transações*

* #### GET
  * #### Retorna todas as transações dos **últimos 60 segundos** `/estatistica`
    <details>
        <summary><strong> Exemplo de resposta 📥 </strong> <sub> (expandir) </sub></summary>

      ```json
          {
              "count": 7,
              "sum": 512.17,
              "avg": 73.16,
              "min": 2.31,
              "max": 92.31
          }
      ```
    </details>
    
  * #### Retorna todas as transações dos **últimos segundos** com um intervalo de tempo customizado `/estatistica/{range}`
    <details>
        <summary><strong> Exemplo de resposta 📥 </strong> <sub> (expandir) </sub></summary>

      ```json
          {
              "count": 7,
              "sum": 512.17,
              "avg": 73.16,
              "min": 2.31,
              "max": 92.31
          }
      ```
    </details>
</details>

## Extras

Vamos propôr a seguir alguns desafios extras caso você queira testar seus conhecimentos ao máximo! Nenhum desses requisitos é obrigatório, mas são desejados e podem ser um diferencial!

1. **Testes automatizados:** Sejam unitários e/ou funcionais, testes automatizados são importantes e ajudam a evitar problemas no futuro. Se você fizer testes automatizados, atente-se na efetividade dos seus testes! Por exemplo, testar apenas os "caminhos felizes" não é muito efetivo. ✅
2. **Containerização:** Você consegue criar meios para disponibilizar sua aplicação como um container? _OBS: Não é necessário publicar o container da sua aplicação!_
3. **Logs:** Sua aplicação informa o que está acontecendo enquanto ela trabalha? Isso é útil para ajudar as pessoas desenvolvedoras a solucionar eventuais problemas que possam ocorrer. ✅
4. **Observabilidade:** Sua API tem algum endpoint para verificação da saúde da aplicação (healthcheck)? ✅
5. **Performance:** Você consegue estimar quanto tempo sua aplicação gasta para calcular as estatísticas? ✅
6. **Tratamento de Erros:** O Spring Boot dá às pessoas desenvolvedoras ferramentas para se melhorar o tratamento de erros padrão. Você consegue alterar os erros padrão para retornar _quais_ erros ocorreram? ✅
7. **Documentação da API:** Você consegue documentar sua API? Existem [ferramentas](https://swagger.io/) e [padrões](http://raml.org/) que podem te ajudar com isso! ✅
8. **Documentação do Sistema:** Sua aplicação provavelmente precisa ser construída antes de ser executada. Você consegue documentar como outra pessoa que pegou sua aplicação pela primeira vez pode construir e executar sua aplicação? ✅
9. **Configurações:** Você consegue deixar sua aplicação configurável em relação a quantidade de segundos para calcular as estatísticas? Por exemplo: o padrão é 60 segundos, mas e se o usuário quiser 120 segundos? ✅

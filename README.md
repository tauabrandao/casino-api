# README #

API para comunicação de Cassino.

### Como configurar ###

Essa aplicação foi construida com o framework Spring Boot e utiliza um SGBD PostgresSQL,
Passos necessários para a utilização:

* Criar um banco de dados Postgres
* Inserir as credenciais de acesso através do arquivo application.properties
* Executar a aplicação através da classe principal.

### Utilização ###

A API faz o uso do Swagger para sua documentação.
É é possível utilizar todas as suas rotas (com excessão da solicitação do token jwt) através da UI do Swagger

Como utilizar:

* Suba a aplicação através da classe CassinoApiApplication.java

Uma vez que a aplicação estiver de pé, é possível acessar a URL de acesso à UI do Swagger

* Acessar http://localhost:8080/swagger-ui.html

Existem 4 controllers mapeados.

É possível fazer o uso da aplicação tanto UI do Swagger quanto por algum client de API (Postman, Insomnia...)

Para fazer o uso da aplicação siga os passos abaixo:

* Crie um Player

Na UI do Swagger, abra o player-controller, e faça o envio de uma requisição POST Criando um novo Player

É necessário apenas enviar no corpo da requisição o JSON com os dados do player, seguindo o formato abaixo:

{
  "dateOfBirth": "2020-10-10",
  "email": "email@email.com",
  "gender": "MALE",
  "name": "John",
  "password": "123456"
}

A requisição deverá retornar 201 e  no corpo da resposta, o player que foi persistido no banco de dados.

Retorno:

{
  "id": 1,
  "name": "John",
  "email": "email@email.com",
  "dateOfBirth": [
    2020,
    10,
    10
  ],
  "gender": "MALE"
}

* Abra um novo Game

Na UI do Swagger, abra o game-controller, e faça uma requisição POST para "Open a new Game"

No corpo da requisição envie o ID do Player que foi criado no passo anterior

{
  "playerId": 1
}

A resposta será um 201 e os dados do game e sessão criados:

{
  "loginFromGameId": "1603050114159_U",
  "passwordFromSessionId": "1603050114168_P"
}

* Enviar chamada de autenticação para obtenção do Token JWT.

Após isso, todas as demais chamadas na API deverão ser autenticadas com um token JWT.

Será necessário enviar uma chamada informando username e password, que são o gameId e sessionId respectivamente.

Essa é a única requisição que não está mapeada na UI do Swagger, para enviá-la será necessário utilizar um client de API.

Envie a requisição para localhost:8080/authenticate passando os dados recebidos na requisição para abrir um novo Game

Note que no username estamos passando o loginFromGameId e no password estamos passando o passwordFromSessionId

{
	"username": "1603050114159_U",
	"password": "1603050114168_P"
}

Essa requisição deverá retornar um 200, e no corpo da resposta estará o Token JWT que será utilizado para efetuar as demais requisições.

OBS: O token expira a cada 10 minutos.

* Obter detalhes do Player

É possível obter os detalhes do jogador através da UI do swagger.

Na UI do Swagger, abra player-controller e envie uma requisição do tipo GET para /player/{playerId} 

Será necessário inserir o token no header da requisição, e enviar como parametro na URL o id do Player que deseja consultar.

A resposta virá como no exemplo abaixo:

{
  "name": "John",
  "dateOfBirth": [
    2020,
    10,
    10
  ],
  "email": "email@email.com",
  "gender": "MALE",
  "playerId": 1,
  "balance": 1800,
  "currency": "USD"
}

* Obter detalhes da Conta (Account)

Da mesma maneira, que o passo anterior, para consultar os detalhes da conta será necessário enviar no header da requisição o token JWT.
Também será necessário passar como parametro na URL o id do Player.

O retorno será algo como:

{
  "id": 1603050076820,
  "active": true,
  "balance": 1800,
  "currency": "USD",
  "player": {
    "id": 1,
    "name": "John",
    "email": "email@email.com",
    "dateOfBirth": [
      2020,
      10,
      10
    ],
    "gender": "MALE"
  }
}

* New Round

Essa opção cria uma nova rodada para enviar apostas

Note que não será possível executar o proximo passo seu que esse seja executado.

Na UI do Swagger, abra a opção round-controller e envie uma requisição GET para /round/start

Para essa requisição é necessário enviar apenas o token no header da requisição.

O retorno será o ID da rodada

{
  "id": 1
}

* Play Game

Essa opção efetua a simulação de um jogo que consiste em retornar um booleano com 50% de chance de ser verdadeiro.

Caso verdadeiro, significa que o jogador venceu o game, e o saldo em sua conta é acrescido do valor de sua aposta.
Caso falso, significa que o jogador perdeu, o valor da aposta é subtraído de sua conta.

Na UI do Swagger, abra a opção game-controller.

Envie uma requisição POST para /game/play.

Para essa requisição é necessário enviar o Token JWT no header bem como o os dados do valor da aposta, gameId, playerId e roundId no corpo da requisição.

OBS: Para obter o game ID, basta copiar o valor de retorno na abertuda de um novo game, e retirar o sufixo mantendo apenas os numeros

 "loginFromGameId": "1603050114159_U", -> 1603050114159
 
 Enviar no corpo da requisição:
 
 {
  "betAmount": 800,
  "gameId": 1603050114159,
  "playerId": 1,
  "roundId": 1
}

O retorno será algo como:

{
  "win": true,
  "newBalance": 1800
}

## API de Reservas

### Objetivo

Desenvolver uma aplicação (Somente o backend) que possibilite realizar o cadastro de
hóspedes e o check in.

Queremos ver como você resolve problemas no seu dia-a-dia. Não há necessidade de
desenvolver o frontend da aplicação, vamos utilizar o Postman para testar sua aplicação.

#### Requisitos funcionais

- Um CRUDL para o cadastro de hóspedes;
- No check in deve ser possível buscar hóspedes cadastrados pelo nome, documento ou telefone;
- Consultar hóspedes que já realizaram o check in e não estão mais no hotel;
- Consultar hóspedes que ainda estão no hotel;
- As consultas devem apresentar o valor (Valor total e o valor da última hospedagem) já gasto pelo hóspede no hotel;

#### Regras de negócio

- Uma diária no hotel de segunda à sexta custa R$120,00;
- Uma diária no hotel em finais de semana custa R$150,00;
- Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo diário, sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;
- Caso o horário da saída seja após às 16:30h deve ser cobrada uma diária extra;

#### Expectativas

- Desenvolva um sistema em Java, com banco de dados PostgreSQL e serviços Rest.
- Desenvolva o problema utilizando frameworks, bibliotecas e/ou componentes que você julgue adequado para resolver o problema;
- Caso seja preciso realizar algum build ou algum passo extra para gerar a sua solução, você deve detalhar o que deve ser feito no arquivo README.md de seu projeto.

## Resolução
Foi utilizado o Spring Boot na resolução do problema dado acima, para rodar a aplicação deve ser criado um banco de dados para esta aplicação, utilize o comando abaixo para criar o banco de dados no PostgreSQL:

```
CREATE DATABASE database-reservas-api
  WITH 
  OWNER = postgres
  ENCODING = 'UTF8'
  CONNECTION LIMIT = -1;
```

Para conexão ao banco de dados o usuário e senha configurados são os seguites, postgres e 123456, caso necessite alterar estas informações acesse o arquivo application.properties que está no diretorio src/main/resources e altere as seguintes linhas:

```
spring.datasource.username=postgres
spring.datasource.password=123456
```

Para compilar o projeto, assumimos que o maven já está configurado na máquina, então é so executar o seguinte comando:

```
mvn clean install
```

Com o build feito com sucesso, vamos rodar o projeto, para isso acesse a pasta target dentro do diretorio do projeto e execute o seguinte comando:

```
javar -jar reservas-api-0.0.1-SNAPSHOT.jar
```

Use Java8 ou posterior!
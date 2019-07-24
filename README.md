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
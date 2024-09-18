# Palace Hotel - Projeto Java Spring

Bem-vindo ao projeto Palace Hotel! Este README fornece instruções sobre como configurar o banco de dados PostgreSQL e como definir o IP no CORS para o seu aplicativo.

## Pré-requisitos

Antes de iniciar o projeto, certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- **Java JDK 17 ou superior**: O Spring Boot requer o JDK 17 ou uma versão superior. [Download do JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Maven**: Para gerenciar as dependências e construir o projeto. [Download do Maven](https://maven.apache.org/download.cgi)

## Configuração do Banco de Dados PostgreSQL

Para configurar o banco de dados PostgreSQL em seu projeto Spring Boot, siga estas etapas:

### Configurações do Banco de Dados

Abra o arquivo `application.properties` localizado em `src/main/resources` e adicione as seguintes configurações:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.url: URL de conexão com o banco de dados. Substitua localhost pelo endereço IP do seu servidor de banco de dados, e 5432 pela porta do PostgreSQL se for diferente. nome_do_banco deve ser substituído pelo nome do seu banco de dados.
spring.datasource.username: Nome de usuário para conectar ao banco de dados.
spring.datasource.password: Senha para o usuário do banco de dados.
spring.jpa.hibernate.ddl-auto: Configura o modo de geração do esquema do banco de dados. Os valores comuns são update, create, create-drop e none.
spring.jpa.show-sql: Se true, exibe as instruções SQL no console.
```

Inicializar o Banco de Dados
Certifique-se de que o PostgreSQL esteja em execução e que o banco de dados especificado exista. Se necessário, crie o banco de dados antes de iniciar o aplicativo.

## Execução
Uma alternativa para executar o projeto é executar o comando mvn spring-boot:run na pasta do projeto



# 💰 Finanças API

API REST para gerenciamento financeiro pessoal, com autenticação JWT, controle de transações, dashboards e resumos por categoria e período.

> Projeto desenvolvido com Java 21 e Spring Boot, seguindo boas práticas de desenvolvimento backend.

-----

## 🚀 Tecnologias

|Tecnologia       |Descrição                        |
|-----------------|---------------------------------|
|Java 21          |Linguagem principal              |
|Spring Boot      |Framework backend                |
|Spring Security  |Segurança da aplicação           |
|JWT              |Autenticação stateless           |
|MySQL            |Banco de dados relacional        |
|JPA / Hibernate  |Mapeamento objeto-relacional     |
|Flyway           |Versionamento e migração do banco|
|Swagger / OpenAPI|Documentação interativa da API   |
|Lombok           |Redução de boilerplate           |

-----

## ✅ Funcionalidades

- 🔐 Autenticação com JWT
- 👤 Cadastro de usuários
- 💸 Cadastro e atualização de transações
- 📄 Paginação de resultados
- 📊 Dashboard financeiro
- 📈 Resumo financeiro geral e mensal
- 🏷️ Resumo por categoria
- 🔍 Filtro de transações por período
- 🛡️ Rotas protegidas por autenticação

-----

## 📌 Endpoints

### Autenticação

```
POST /login
```

### Transações

```
POST   /transacoes
GET    /transacoes
PUT    /transacoes/{id}
GET    /transacoes/{id}
GET    /transacoes/despesas
GET    /transacoes/receitas
GET    /transacoes/resumo
GET    /transacoes/resumo/{ano}/{mes}
GET    /transacoes/filtro
GET    /transacoes/resumo/categoria
GET    /transacoes/dashboard
```

-----

## ⚙️ Como executar localmente

### Pré-requisitos

- Java 21+
- MySQL
- Maven

### 1. Clone o repositório

```bash
git clone https://github.com/MatheusK27/Financas-api
cd financas-api
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto (veja o `.env.example`):

```env
DB_URL=jdbc:mysql://localhost:3306/financas_api
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta
```

> ⚠️ Nunca commite credenciais reais. Use sempre variáveis de ambiente.

### 3. Execute o projeto

```bash
./mvnw spring-boot:run
```

-----

## 🔒 Autenticação

A API utiliza autenticação via **JWT (Bearer Token)**.

**1. Faça login:**

```http
POST /login
Content-Type: application/json

{
  "email": "usuario@email.com",
  "senha": "suasenha"
}
```

**2. Use o token retornado no header das próximas requisições:**

```http
Authorization: Bearer <seu_token>
```

-----

## 📚 Documentação Swagger

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui.html
```

-----

## 📁 Estrutura do projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/financas/api/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── model/
│   │       ├── dto/
│   │       └── security/
│   └── resources/
│       ├── application.properties
│       └── db/migration/
└── test/
```

-----

## 👨‍💻 Autor

**Matheus Klein**

[![GitHub](https://img.shields.io/badge/GitHub-MatheusK27-181717?style=flat&logo=github)](https://github.com/MatheusK27)

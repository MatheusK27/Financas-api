# 💰 Finanças API

API REST desenvolvida com Spring Boot para gerenciamento financeiro.

---

## 🚀 Tecnologias

| Tecnologia | Descrição |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot | Framework backend |
| Spring Security | Segurança da aplicação |
| JWT | Autenticação |
| MySQL | Banco de dados |
| JPA/Hibernate | Persistência |
| Flyway | Versionamento do banco |
| Swagger/OpenAPI | Documentação |
| Lombok | Redução de boilerplate |

---

## ✅ Funcionalidades

- 🔐 Login com JWT
- 👤 Cadastro de usuários
- 💸 Cadastro de transações
- ✏️ Atualização de transações
- 📄 Paginação de resultados
- 📊 Dashboard financeiro
- 📈 Resumo financeiro geral
- 📅 Resumo financeiro mensal
- 🏷️ Resumo por categoria
- 🔍 Filtro por período
- 🛡️ Rotas protegidas por autenticação

---

## 📌 Endpoints principais

### Autenticação

```http
POST /login
```

### Transações

```http
POST /transacoes
GET /transacoes
PUT /transacoes/{id}
GET /transacoes/{id}
GET /transacoes/despesas
GET /transacoes/receitas
GET /transacoes/resumo
GET /transacoes/resumo/{ano}/{mes}
GET /transacoes/filtro
GET /transacoes/resumo/categoria
GET /transacoes/dashboard
```

---

## ⚙️ Como executar

### Clone o projeto

```bash
git clone https://github.com/MatheusK27/Financas-api
```

### Entre na pasta

```bash
cd financas-api
```

### Configure o application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/financas_api
spring.datasource.username=desenvolvedor
spring.datasource.password=123456
jwt.secret=sua_chave_secreta
```

### Execute o projeto

```bash
./mvnw spring-boot:run
```

---

## 🔒 Autenticação

A API utiliza autenticação JWT.

Após realizar login:

```http
POST /login
```

utilize o token no header:

```http
Authorization: Bearer seu_token
```

---

## 📚 Documentação Swagger

```http
http://localhost:8080/swagger-ui.html
```

---

## 👨‍💻 Autor

Matheus Klein
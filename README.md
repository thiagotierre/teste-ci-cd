# Sistema de Gestão de Oficina - API REST

## 📖 Sobre o Projeto

Este projeto é uma API RESTful para um sistema de gestão de oficina mecânica, desenvolvido em **Java 21** com **Spring Boot**. A aplicação permite o gerenciamento completo de clientes, veículos, peças, serviços e Ordens de Serviço (OS), seguindo uma arquitetura limpa e orientada a casos de uso.

A API utiliza autenticação baseada em **JWT (JSON Web Token)** para proteger os endpoints, e possui um sistema de papéis de usuário (`ADMIN`, `MECHANIC`, `ATTENDANT`) para controle de acesso. A estrutura do banco de dados é gerenciada de forma automatizada através do **Liquibase**, garantindo consistência e versionamento das migrações.

---

## 🛠️ Tecnologias Utilizadas

O projeto foi construído com as seguintes tecnologias e bibliotecas:

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.5.0
* **Acesso a Dados:** Spring Data JPA
* **Banco de Dados:** PostgreSQL
* **Migrations de Banco:** Liquibase
* **Segurança:** Spring Security com autenticação JWT
* **Documentação da API:** SpringDoc (Swagger/OpenAPI)
* **Containerização:** Docker e Docker Compose
* **Testes:** JUnit 5, Mockito e Testcontainers para testes de integração
* **Validação:** Jakarta Bean Validation (com validadores customizados para CPF/CNPJ)
* **Utilitários:** Lombok
* **Build Tool:** Maven

---

## ✨ Funcionalidades

A API oferece as seguintes funcionalidades principais:

* **Autenticação e Autorização:** Login via JWT e controle de acesso baseado em papéis (`UserRole`).
* **Gerenciamento de Clientes, Veículos, Peças e Serviços:** Operações CRUD completas para as entidades de base do sistema.
* **Gestão de Ordens de Serviço (Work Orders):**
    * Criação de OS associando cliente, veículo, peças e serviços.
    * Ciclo de vida completo com gestão de status (Recebido, Em Diagnóstico, Aguardando Aprovação, Recusado, Em Andamento, Finalizado, Entregue).
    * Atribuição de mecânicos e tomada de decisão (aprovar/recusar) pelo cliente.
    * Cálculo automático do valor total da OS.
* **Controle de Estoque:** Lógica para reservar e retornar peças ao estoque durante o ciclo de vida da OS.
* **Histórico e Métricas:** Rastreamento do histórico de status de cada OS e cálculo do tempo médio de conclusão.

---

## 🚀 Como Executar o Projeto com Docker

#### 1. Pré-requisitos

* **Docker**
* **Docker Compose**

#### 2. Clone o Repositório
```bash
git clone https://github.com/J-Lembeck/challengeOne
cd challengeOne
```

#### 3. Inicie a Aplicação
Com o terminal aberto na raiz do projeto (onde se encontra o arquivo `docker-compose.yml`), execute o comando abaixo:
```bash
docker-compose up --build -d
```

#### 4. Acesso à API
A aplicação estará disponível em `http://localhost:8080/api`.

#### 5. Usuário Administrador Padrão
Um usuário administrador é criado por padrão pelo Liquibase para facilitar os primeiros testes:
* **Email:** `administrador@adm.com.br`
* **Senha:** `123`

#### 6. Acesse a Documentação Interativa (Swagger)
Para explorar e testar todos os endpoints, acesse a documentação do Swagger UI no seu navegador:
**[http://localhost:8080/api/documentation](http://localhost:8080/api/documentation)**

#### 7. Como Parar a Aplicação
```bash
docker-compose down
```

---

## 📄 Documentação da API (Swagger)

A API possui uma documentação interativa gerada com Swagger (OpenAPI). Após iniciar a aplicação, pode aceder à interface do Swagger no seguinte URL:

**[http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)**

---

## <caption>Endpoints da API</caption>

Todos os endpoints, exceto `/auth/login` e os de consulta pública, requerem um token JWT no cabeçalho `Authorization: Bearer <token>`. As respostas seguem um formato padrão `ResponseApi<T>`.

### Autenticação (`/auth`)
* `POST /login`: Autentica um usuário e retorna um token JWT.

### Usuários (`/users`)
* `POST /`: Cria um novo usuário.

### Clientes (`/customers`)
* `POST /`: Cria um novo cliente.
* `GET /{id}`: Busca um cliente por ID.
* `GET /findByCpfCnpj/{cpfCnpj}`: Busca um cliente pelo CPF/CNPJ completo.
* `PUT /{id}`: Atualiza um cliente.
* `DELETE /{id}`: Remove um cliente.

### Veículos (`/vehicles`)
* `POST /`: Adiciona um novo veículo a um cliente.
* `GET /{id}`: Busca um veículo por ID.
* `GET /by-plate?plate={plate}`: Busca um veículo pela placa.
* `DELETE /{id}`: Remove um veículo.

### Peças (`/parts`) e Serviços (`/services`)
* Possuem endpoints CRUD completos (`POST`, `GET`, `PUT`, `DELETE`).

### Ordens de Serviço (`/work-orders`)
* `POST /`: Abre uma nova Ordem de Serviço.
* `GET /list`: Lista todas as Ordens de Serviço com filtros por status e ordenação.
* `GET /{id}`: Busca uma Ordem de Serviço específica.
* `GET /cpf/{cpf}/latest-work-order-history`: Busca o histórico de OS de um cliente pelo CPF.
* `PATCH /{id}/status`: Altera o status de uma OS (ex: `IN_PROGRESS`).
* `PATCH /{id}/decision`: Permite ao cliente aceitar (`true`) ou recusar (`false`) um orçamento.
* `PATCH /{id}/assign-mechanic`: Atribui um mecânico a uma OS.
* `PATCH /{id}/update-items`: Adiciona novas peças ou serviços a uma OS.
* `PATCH /{id}/finalize`: Finaliza uma OS (muda o status para `COMPLETED`).
* `PATCH /{id}/delivered`: Marca uma OS como entregue ao cliente.
* `GET /calculate-avarage-time`: Retorna o tempo médio de conclusão das Ordens de Serviço.

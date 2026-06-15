# 📝 Gerenciador de Tarefas — API REST

> API RESTful de gerenciamento de tarefas com autenticação de usuários, construída com Java 17 e Spring Boot 3.

---

## 📌 Sobre o Projeto

Este projeto é um clone simplificado do Todoist, desenvolvido com o objetivo de praticar a construção de APIs REST seguras com Java e Spring Boot.

A aplicação permite que usuários se cadastrem, façam login e gerenciem suas próprias tarefas, com controle de acesso garantindo que cada usuário veja e edite apenas os seus dados.

---

## ✨ Funcionalidades

- ✅ Cadastro de usuários com senha criptografada (BCrypt)
- ✅ Autenticação via HTTP Basic Auth
- ✅ Filtro de segurança customizado por requisição (OncePerRequestFilter)
- ✅ Criação de tarefas com título, descrição, prioridade e datas
- ✅ Validação de datas (início e fim devem ser futuras e consistentes)
- ✅ Listagem de tarefas por usuário autenticado
- ✅ Atualização parcial de tarefas (apenas campos enviados são alterados)
- ✅ Isolamento de dados: cada usuário acessa somente suas tarefas
- ✅ Tratamento global de exceções
- ✅ Containerização com Docker

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring Data JPA | — |
| Banco de dados H2 | In-memory |
| BCrypt | 0.10.2 |
| Lombok | 1.18.30 |
| Maven | — |
| Docker | — |

---

## 📁 Estrutura do Projeto

```
src/main/java/Gerenciador/de/tarefas/
│
├── user/
│   ├── UserController.java       # Endpoint de cadastro de usuários
│   ├── UserModel.java            # Entidade de usuário (tb_users)
│   └── IUserRepository.java      # Repositório JPA
│
├── task/
│   ├── TaskController.java       # Endpoints de tarefas (POST, GET, PUT)
│   ├── TaskModel.java            # Entidade de tarefa (tb_tasks)
│   ├── ITaskRepository.java      # Repositório JPA
│   └── filter/
│       └── FilterTaskAuth.java   # Filtro de autenticação Basic Auth
│
├── errors/
│   └── ExceptionHandlerController.java  # Tratamento global de exceções
│
└── utils/
    └── Utils.java                # Utilitário para atualização parcial (PATCH-like)
```

---

## 🚀 Como Executar

### Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/) *(opcional)*

---

### ▶️ Rodando localmente

```bash
# Clone o repositório
git clone https://github.com/Gabriel0441/todoist.git
cd todoist/Todoist

# Execute com Maven
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

O console do H2 estará disponível em: `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:todolist`
- **Usuário:** `admin`
- **Senha:** `admin`

---

### 🐳 Rodando com Docker

```bash
# Build da imagem
docker build -t todoist-api .

# Executar o container
docker run -p 8080:8080 todoist-api
```

---

## 📡 Endpoints

### 👤 Usuários

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| POST | `/users/` | Cadastrar novo usuário | ❌ |

**Body (JSON):**
```json
{
  "name": "Gabriel",
  "username": "gabriel123",
  "password": "minhasenha"
}
```

---

### ✅ Tarefas

> Todos os endpoints de tarefas exigem autenticação Basic Auth.

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/tasks/` | Criar nova tarefa |
| GET | `/tasks/` | Listar tarefas do usuário autenticado |
| PUT | `/tasks/{id}` | Atualizar tarefa (apenas campos enviados) |

**Body — Criar tarefa (JSON):**
```json
{
  "title": "Estudar Spring Boot",
  "description": "Revisar filtros de autenticação",
  "priority": "ALTA",
  "startAt": "2025-06-15T09:00:00",
  "endAt": "2025-06-15T11:00:00"
}
```

**Regras de validação:**
- `title` — máximo de 50 caracteres
- `startAt` e `endAt` — devem ser datas futuras
- `startAt` — deve ser anterior a `endAt`

---

## 🔐 Autenticação

A API utiliza **HTTP Basic Auth**. Envie as credenciais codificadas em Base64 no header de cada requisição para `/tasks/`:

```
Authorization: Basic <base64(username:password)>
```

Exemplo com curl:
```bash
curl -u gabriel123:minhasenha http://localhost:8080/tasks/
```

---

## 🧠 Decisões Técnicas

**BCrypt para senhas** — as senhas nunca são armazenadas em texto puro. O BCrypt aplica hash com salt automático a cada cadastro.

**OncePerRequestFilter** — filtro customizado que intercepta todas as requisições para `/tasks/`, valida as credenciais e injeta o ID do usuário no request para uso nos controllers.

**UUID como identificador** — usuários e tarefas utilizam UUID como chave primária, evitando IDs sequenciais previsíveis.

**Atualização parcial com BeanUtils** — o método `Utils.copyNonNullProperties` copia apenas os campos não-nulos do body recebido, permitindo atualizações parciais sem sobrescrever dados existentes.

**Banco H2 in-memory** — utilizado para facilitar o desenvolvimento e testes sem necessidade de instalar banco de dados localmente. Os dados são resetados a cada reinicialização.

---

## 👨‍💻 Autor

**Gabriel Coutinho Nascimento**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-gabriel--coutinho-blue?style=flat&logo=linkedin)](https://linkedin.com/in/gabriel-coutinho-0697612b1)
[![GitHub](https://img.shields.io/badge/GitHub-Gabriel0441-black?style=flat&logo=github)](https://github.com/Gabriel0441)

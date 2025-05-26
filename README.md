# Inova Rede - Back-end

Este repositório contém o back-end da plataforma **Inova Rede**, um sistema colaborativo voltado à conexão entre estudantes, professores e profissionais para o desenvolvimento de projetos acadêmicos e científicos. A aplicação foi desenvolvida em **Java** com o framework **Spring Boot**, seguindo arquitetura RESTful e integrando-se a um banco de dados **MySQL**.

## 📌 Tecnologias Utilizadas

- Java 17  
- Spring Boot  
  - Spring Web  
  - Spring Data JPA  
  - Spring Security (em desenvolvimento)  
- MySQL  
- Docker  
- Maven  

## 📁 Estrutura do Projeto

- `controller/`: Gerencia as requisições HTTP da API.  
- `service/`: Contém a lógica de negócio.  
- `repository/`: Interface de acesso ao banco de dados.  
- `entity/`: Classes que representam as tabelas do banco (JPA).  
- `dto/`: Objetos de transferência de dados (em expansão).  

## ⚙️ Como Executar o Projeto

### 1. Pré-requisitos
- Java 17+  
- Maven  
- Docker e Docker Compose (opcional)

### 2. Executando com Docker

```bash
# Suba os containers do banco e da aplicação
docker-compose up --build

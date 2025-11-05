# Projeto - Gerenciador de Tarefas

Sistema completo de gerenciamento de tarefas desenvolvido com Spring Boot, incluindo CRUD completo, validações, integração com API REST, banco de dados H2 e interface web com Thymeleaf e Bootstrap.

##  Tecnologias

- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **Thymeleaf** (template engine)
- **Bootstrap 5.3.0** (interface responsiva)
- **Spring Validation** (validações de formulário)
- **Maven** (gerenciamento de dependências)

##  Funcionalidades

-  Criar, listar, editar e deletar tarefas
-  Marcar tarefas como concluídas/pendentes
-  Filtrar tarefas por status (todas, pendentes, concluídas)
-  Validações de formulário
-  API REST completa para integração
-  Banco H2 

## Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

### Executando o Projeto

1. Clone o repositório ou navegue até o diretório do projeto

2. Execute o projeto com Maven:
```bash
mvn spring-boot:run
```

3. Acesse a aplicação:
   - Interface Web: http://localhost:8080
   - API REST: http://localhost:8080/api/tarefas
   - Console H2: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:tarefasdb`
     - Usuário: `sa`
     - Senha: (em branco)

##  Endpoints da API REST

### GET /api/tarefas
Lista todas as tarefas
- Query params opcionais: `?concluida=true` ou `?concluida=false`

### GET /api/tarefas/{id}
Busca uma tarefa por ID

### POST /api/tarefas
Cria uma nova tarefa
```json
{
  "titulo": "Nova Tarefa",
  "descricao": "Descrição da tarefa",
  "concluida": false
}
```

### PUT /api/tarefas/{id}
Atualiza uma tarefa existente

### DELETE /api/tarefas/{id}
Deleta uma tarefa

### PATCH /api/tarefas/{id}/toggle-status
Alterna o status da tarefa (concluída/pendente)

### GET /api/tarefas/buscar?titulo=...
Busca tarefas por título (busca parcial, case-insensitive)

##  Interface Web

- **/** - Página inicial
- **/tarefas** - Lista todas as tarefas
- **/tarefas/novo** - Formulário de nova tarefa
- **/tarefas/{id}/editar** - Formulário de edição

##  Validações

- **Título**: Obrigatório, entre 3 e 100 caracteres
- **Descrição**: Opcional, máximo 500 caracteres

## Banco de Dados

O projeto utiliza H2 Database em memória. Os dados são perdidos quando a aplicação é encerrada. Para persistir os dados, configure um banco de dados permanente no `application.properties`.

##  Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/tandre/
│   │   ├── ProjetoTarefaApplication.java
│   │   ├── controller/
│   │   │   ├── HomeController.java
│   │   │   ├── TarefaController.java
│   │   │   └── api/
│   │   │       └── TarefaApiController.java
│   │   ├── model/
│   │   │   └── Tarefa.java
│   │   ├── repository/
│   │   │   └── TarefaRepository.java
│   │   └── service/
│   │       └── TarefaService.java
│   └── resources/
│       ├── application.properties
│       └── templates/
│           ├── index.html
│           └── tarefas/
│               ├── listar.html
│               └── formulario.html
└── pom.xml
```

##  Configurações

As configurações principais estão em `src/main/resources/application.properties`:
- Porta do servidor: 8080
- Banco H2 em memória
- Console H2 habilitado
- Thymeleaf cache desabilitado 



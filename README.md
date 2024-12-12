# Aplicação Screenmatch

Screenmatch é uma aplicação baseada em Java que permite aos usuários gerenciar e buscar séries de TV e episódios. Utiliza Spring Boot para o backend e Maven para gerenciamento de dependências.

## Funcionalidades

- Adicionar e gerenciar séries de TV
- Adicionar e gerenciar episódios para cada série
- Funcionalidade de busca para séries e episódios
- Integração com um repositório para persistência de dados

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- JPA (Jakarta Persistence API)
- H2 Database (para desenvolvimento e testes)
- PostgreSQL

## Começando

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6.0 ou superior

### Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/sudoaptgetmach/screenmatch.git
    cd screenmatch
    ```

2. Construa o projeto usando Maven:
    ```sh
    mvn clean install
    ```

3. Execute a aplicação:
    ```sh
    mvn spring-boot:run
    ```

### Uso

Uma vez que a aplicação esteja em execução, você pode acessá-la via interface de linha de comando. A classe `AppManager` fornece um menu para interagir com a aplicação.

### Configuração do Banco de Dados

A aplicação usa um banco de dados H2 em memória por padrão. Você pode configurar as configurações do banco de dados no arquivo `application.properties` localizado no diretório `src/main/resources`.

### Exemplo

Para adicionar uma nova série e episódios, siga as instruções na interface de linha de comando fornecida pela classe `AppManager`.

## Estrutura do Projeto

- `src/main/java/org/mach/screenmatch`: Contém o código principal da aplicação
- `src/main/resources`: Contém arquivos de configuração
- `src/test/java/org/mach/screenmatch`: Contém casos de teste

## Contribuindo

Contribuições são bem-vindas! Por favor, faça um fork do repositório e envie um pull request.

## Licença

Este projeto é licenciado sob a Licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
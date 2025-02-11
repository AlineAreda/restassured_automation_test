# 💻 Automation RestAssured

Este projeto é uma suíte de testes automatizados para APIs utilizando o RestAssured, integrado com o JUnit 5 para a execução dos testes e Allure para a geração de relatórios detalhados. O objetivo é garantir a qualidade das APIs do projeto backend [E2EBurguer](https://github.com/AlineAreda/e2eburguer_api)


# 👨🏻‍💻Tecnologias Utilizadas

-   **Java 17**: Linguagem de programação principal.
-   **Maven**: Ferramenta de gerenciamento de dependências e build.
-   **JUnit 5**: Framework de testes para Java.
-   **RestAssured 5.4.0**: Biblioteca para testes de APIs REST.
-   **Allure**: Ferramenta de geração de relatórios de teste.
-   **Owner API**: Biblioteca para gerenciamento de configurações.

## Estrutura do Projeto

-   **src/test/java**: Contém as classes de testes.
-   **src/test/resources**: Contém arquivos de configuração e dados de teste.
-   **target**: Diretório onde são gerados os resultados da compilação e dos testes, incluindo o diretório `allure-results` que armazena os resultados para o Allure. *diretório ignorado pelo Git.

## 🤖 Configuração e Execução
1. Clone o repositório para sua máquina local:

```bash
git clone https://github.com/seu-usuario/automation-restassured.git
cd automation-restassured
```

### Executar os Testes
2. Execute os testes usando o Maven:
```bash
mvn clean test
```

### Gerar Relatório Allure

3. Após executar os testes, você pode gerar e visualizar o relatório do **Allure** com:

```bash
mvn allure:serve
```

### Visão Relatório de execução Allure
![Image](https://github.com/user-attachments/assets/292c8438-b63a-4ff0-8ef8-9a2a5b2fb16e)

### Visão Relatório Gráfico Allure
![Image](https://github.com/user-attachments/assets/96cb13b4-7dd5-42ed-8045-2924fbe7abcc)

### Suítes: Relatório Allure
![Image](https://github.com/user-attachments/assets/7cee61db-5c4a-4dcd-8558-69a86048a97a)

## Estrutura de Arquivos


```bash
automation-restassured/
│
├── src/
│   └── test/
│       ├── java/
│       │   └── com/e2eburguer/
│       │       ├── config/                   # Configurações e classes base
│       │       │   ├── BaseTest.java
│       │       │   └── Configuracoes.java
│       │       ├── dataFactory/              # Fábricas de dados para criação de objetos de teste
│       │       │   ├── OrderDataFactory.java
│       │       │   ├── ProductDataFactory.java
│       │       │   └── UserDataFactory.java
│       │       ├── pojo/                     # Classes POJO representando os objetos principais
│       │       │   ├── Category.java
│       │       │   ├── Item.java
│       │       │   ├── Order.java
│       │       │   ├── Product.java
│       │       │   └── User.java
│       │       ├── tests/                    # Classes de teste
│       │       │   ├── CategoryTest.java
│       │       │   ├── LoginTest.java
│       │       │   ├── OrderTest.java
│       │       │   ├── ProductTest.java
│       │       │   └── UserTest.java
│       │       └── utils/                    # Utilitários
│       │           └── Utils.java
│       └── resources/
│           ├── files/                        # Arquivos de suporte para testes
│           │   └── burguer.jpeg
│           ├── payloads/                     # JSONs de payload para requisições
│           │   ├── addItem.json
│           │   ├── login.json
│           │   └── userDuplicate.json
│           ├── properties/                   # Arquivos de configuração de ambientes
│           └── schemas/                      # Schemas JSON para validação
│               └── postUserSchema.json
├── .gitignore                                # Arquivos e diretórios ignorados pelo Git
├── pom.xml                                   # Arquivo de configuração do Maven
└── README.md                                 # Documentação do projeto

```


## 📌 Notas Gerais

Exemplo de construção de Testes Backend em Java com RestAssured !

Feito com ❤️ por [Aline Areda](https://github.com/AlineAreda) 😊

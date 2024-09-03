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
-   **target**: Diretório onde são gerados os resultados da compilação e dos testes, incluindo o diretório `allure-results` que armazena os resultados para o Allure.
-
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
![overview testes e2eburguer](https://github.com/user-attachments/assets/238a151e-4110-42f5-b01d-7f646e552c2f)

### Suítes: Relatório Allure
![Allure report - suites e2eburguer](https://github.com/user-attachments/assets/3968cfe0-9790-4479-b931-a496a16debba)

## Estrutura de Arquivos


```bash
automation-restassured/
│
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/e2eburguer/tests/                            # Classes de teste
│   │   └── resources/
│   │       └── properties/                                                  # Configurações por ambiente
│
├── target/                                                                        # Diretório de build e resultados
│   ├── allure-results/                                                     # Resultados do Allure
│   └── surefire-reports/                                                # Relatórios do Surefire
│
├── .gitignore                                                                 # Arquivos e diretórios ignorados pelo Git
├── pom.xml                                                                   # Arquivo de configuração do Maven
└── README.md                                                          # Este arquivo
```


## 📌 Notas Gerais

Exemplo de construção de Testes Backend em Java com RestAssured !

Feito com ❤️ por [Aline Areda](https://github.com/AlineAreda) 😊

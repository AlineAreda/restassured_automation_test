# 💻 Automation RestAssured

Este projeto é uma suíte de testes automatizados para APIs utilizando **RestAssured**, integrado com **JUnit 5** para a execução dos testes e **Allure** para a geração de relatórios detalhados.  
O objetivo é garantir a **qualidade das APIs** do projeto backend [**E2EBurguer**](https://github.com/AlineAreda/e2eburguer_api).

----------

## **🚀 Tecnologias Utilizadas**

-   **Java 17** → Linguagem de programação principal.

-   **Maven** → Ferramenta de gerenciamento de dependências e build.

-   **JUnit 5** → Framework de testes para Java.

-   **RestAssured 5.4.0** → Biblioteca para testes de APIs REST.

-   **Allure** → Ferramenta de geração de relatórios de teste.

-   **Owner API** → Biblioteca para gerenciamento de configurações.

-   **Docker** → Para criação de containers e execução isolada dos testes.

-   **Jenkins** → Para execução contínua e integração com pipelines de CI/CD.


----------

## **📂 Estrutura do Projeto**

### **📌 Diretórios Principais**

-   📁 `src/test/java` → Contém as classes de testes automatizados.

-   📁 `src/test/resources` → Contém arquivos de configuração, payloads e schemas JSON.

-   📁 `target` → Diretório onde são gerados os resultados da compilação e dos testes.  
    ⚠️ _Diretório ignorado pelo Git (_`_.gitignore_`_), pois é gerado automaticamente._


----------

## **⚙️ Configuração e Execução**

### **1️⃣ Clonar o Repositório**

```
git clone https://github.com/seu-usuario/automation-restassured.git
cd automation-restassured
```

### **2️⃣ Executar os Testes**

Para rodar os testes automatizados, execute:

```
mvn clean test
```

### **3️⃣ Gerar Relatório Allure**

Após a execução dos testes, gere o relatório Allure com:

```
mvn allure:report
```

Para visualizar o relatório no navegador:

```
mvn allure:serve
```

Isso abrirá o **relatório interativo** em seu navegador.


## **⚙️  Execução Jenkins**

### **1️⃣ Clonar o Repositório**

```
git clone https://github.com/seu-usuario/automation-restassured.git
cd automation-restassured
```

### **2️⃣ Subir o Ambiente com Docker**

Caso queira rodar os testes dentro de um container **Docker**, siga estes passos:

```
docker-compose up -d
```

> Isso iniciará o Jenkins e qualquer outro serviço necessário dentro de containers.

### **3️⃣ Executar os Testes dentro do Container**

```
docker exec -it nome_do_container mvn clean test
```

> Substitua `nome_do_container` pelo **nome do container Maven** que estiver rodando.

### **4️⃣ Executar os Testes via Jenkins (CI/CD)**

Se os testes forem executados via **Jenkins dentro de um container Docker**, siga estas etapas:

1️⃣ **Acesse o Jenkins**

-   Se o Jenkins estiver rodando via Docker, acesse:

    ```
    http://localhost:8080/jenkins
    ```

-   Se estiver rodando em outro servidor, acesse via IP do servidor.


2️⃣ **Configurar Pipeline Script from SCM no Jenkins**

-   Vá até **Configurar > Pipeline**.

-   Selecione **"Pipeline Script from SCM"**.

-   Escolha **"Git"** como SCM e adicione a URL do repositório:

    ```
    https://github.com/perfil/automation-restassured.git
    ```

-   No campo **"Branch Specifier"**, defina `main` (ou outro branch usado).

-   No campo **"Script Path"**, defina:

    ```
    Jenkinsfile
    ```

-   Clique em **Salvar** e execute o pipeline.


3️⃣ **Rodar o Pipeline no Jenkins**

-   Após configurar, clique em **"Build Now"** para executar os testes.

- ✅ **Relatórios gerados e publicados automaticamente no Jenkins** 📊
----------

## **📊 Visão dos Relatórios Allure**

### **Relatório de Execução**

> ⚠️ _Se estiver rodando localmente, você pode gerar e visualizar os relatórios conforme instruções acima._

### **Relatório Gráfico**

> 📊 Apresenta estatísticas detalhadas sobre a execução dos testes.

### **Suítes de Testes**

> 📌 Exibe todas as suítes de testes, tempo de execução e status.

----------

## **📌 Estrutura de Arquivos**

```
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
├── Dockerfile                               # Configuração do container Docker
├── docker-compose.yml                       # Arquivo de configuração para Docker Compose
├── Jenkinsfile                              # Pipeline de integração contínua no Jenkins
├── .gitignore                               # Arquivos e diretórios ignorados pelo Git
├── pom.xml                                  # Arquivo de configuração do Maven
└── README.md                                # Documentação do projeto
```

----------

## **📌 Notas Gerais**

🔹 **Exemplo prático de testes automatizados de APIs em Java utilizando RestAssured e Allure.**  
🔹 **Fácil integração com pipelines de CI/CD (Jenkins, GitHub Actions).**  
🔹 **Relatórios detalhados com Allure para visualização interativa dos resultados.**

----------

## **💡 Sobre o Autor**

Feito com ❤️ por [Aline Areda](https://github.com/AlineAreda) 😊  
📌 _Contribuições são bem-vindas!_
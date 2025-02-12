# Usa a versão mais recente do Jenkins LTS com JDK 17
FROM jenkins/jenkins:2.492.1-jdk17

# Define usuário root para instalação de dependências
USER root

# Atualiza os pacotes e instala dependências essenciais
RUN apt-get update && apt-get install -y \
    lsb-release \
    maven \
    curl \
    unzip

# Adiciona repositório e instala Docker CLI
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg && \
  echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
  apt-get update && \
  apt-get install -y docker-ce-cli

# Instala o Allure Commandline
RUN curl -o allure-2.23.0.tgz -Ls https://github.com/allure-framework/allure2/releases/download/2.23.0/allure-2.23.0.tgz \
    && tar -zxvf allure-2.23.0.tgz -C /opt/ \
    && ln -s /opt/allure-2.23.0/bin/allure /usr/bin/allure \
    && rm allure-2.23.0.tgz

# Retorna para o usuário Jenkins
USER jenkins

# Define diretório de trabalho
WORKDIR /var/jenkins_home/workspace

# Copia apenas arquivos necessários para dentro do container
COPY pom.xml /var/jenkins_home/workspace/
COPY src/ /var/jenkins_home/workspace/src/

# Baixa dependências do Maven para cache
RUN mvn dependency:go-offline

# Instala plugins do Jenkins necessários
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow allure-jenkins-plugin"

# Define comando padrão para iniciar Jenkins
CMD ["jenkins.sh"]
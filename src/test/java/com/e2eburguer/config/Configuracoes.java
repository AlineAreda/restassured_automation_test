package com.e2eburguer.config;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/test/resources/propreties/dev.properties"})
public interface Configuracoes extends Config {

    @Key("baseURI")
    String baseURI();

    @Key("port")
    Integer port();

    @Key("basePath")
    String basePath();

}





package com.e2eburguer.config;

import org.aeonbits.owner.Config;


@Config.Sources({"classpath:properties/${ENV}.properties",
        "classpath:properties/hml.properties"})
public interface Configuracoes extends Config {

    @Key("baseURI")
    String baseURI();

    @Key("port")
    Integer port();

    @Key("basePath")
    String basePath();

}





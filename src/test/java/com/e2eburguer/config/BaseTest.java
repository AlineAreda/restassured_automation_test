package com.e2eburguer.config;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class BaseTest {

    @BeforeAll()
    public static void setUp(){
        Configuracoes config = ConfigFactory.create(Configuracoes.class);
        baseURI = config.baseURI();
        port = config.port();
        basePath = config.basePath();

    }

}

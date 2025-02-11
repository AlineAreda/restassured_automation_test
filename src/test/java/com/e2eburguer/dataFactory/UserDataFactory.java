package com.e2eburguer.dataFactory;

import com.e2eburguer.pojo.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class UserDataFactory {

    public static final String DEFAULT_NAME = "Gestão E2E";
    public static final String DEFAULT_EMAIL = "gestao@e2eburguer.com.br";
    public static final String DEFAULT_PASSWORD = "Teste@123!";
    public static final String ID_USER = "e6660b90-d6df-432c-8f6d-108ee9d3eb64";
    public static final String DEFAULT_CONFIRMPASSAWORD = "Teste@123!";
    public static final Boolean DEFAULT_PROFILE = true;

    public static User userIsGestao(){
        User user = new User();

        user.setName(DEFAULT_NAME);
        user.setEmail(DEFAULT_EMAIL);
        user.setPassword(DEFAULT_PASSWORD);
        user.setConfirmPassword(DEFAULT_CONFIRMPASSAWORD);
        user.setIsGestao(DEFAULT_PROFILE);

        return user;
    }

    public static User loadUser(String key) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Lê o arquivo JSON e mapeia para um Map<String, User>
        Map<String, User> usersMap = objectMapper.readValue(
                new FileInputStream("src/test/resources/payloads/postUsers.json"),
                new TypeReference<Map<String, User>>() {}
        );

        // Retorna o objeto User correspondente à chave
        return usersMap.get(key);
    }


    public static User login() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new FileInputStream("src/test/resources/payloads/postLogin.json"), User.class);
    }


    public static User userId(){
        User userID = new User();
        userID.setName(DEFAULT_NAME);
        userID.setEmail(DEFAULT_EMAIL);
        userID.setUserId(ID_USER);
        userID.setIsGestao(DEFAULT_PROFILE);

        return userID;
    }

    public static User updateUser(){
        User user = new User();

        user.setName("Maria do Carmo");
        user.setEmail("m.carmo@e2ebruguer.com.br");
        user.setPassword("Teste@123!");
        user.setConfirmPassword("Teste@123!");
        user.setIsGestao(true);
        user.setUserId("b01e394c-02e0-486f-8e2d-7bceb55350a2");

        return user;
    }


}


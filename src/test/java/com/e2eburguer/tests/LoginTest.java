package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayName("Testes do endpoint /session")
public class LoginTest extends BaseTest {

    @DisplayName("Autenticação perfil gestão com sucesso")
    @Test
    public void testShouldLogInUser() throws IOException {
        User user = UserDataFactory.login();

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("session")
        .then()
                .statusCode(200);
    }

    @DisplayName("Autenticação perfil salão com sucesso")
    @Test
    public void testShouldLogInProfileSalao() throws IOException {
        User user = UserDataFactory.login();
        user.setEmail("salao@e2eburguer.com.br");

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("session")
        .then()
                .statusCode(200);
    }

    @DisplayName("Não deve logar quando e-mail inválido.")
    @Test
    public void testShouldNotAcceptInvalidEmail() throws IOException {
        User user = UserDataFactory.login();
        user.setEmail("invalid@e2eburguer.com.br");


        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("session")
        .then()
                .statusCode(401)
                .body("error", is("Usuário e/ou Senha incorretos."))
        ;
    }

    @DisplayName("Não deve logar quando senha inválida.")
    @Test
    public void testShouldNotAcceptInvalidPassword() throws IOException {
        User user = UserDataFactory.login();
        user.setPassword("Teste@123");


        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("session")
        .then()
                .statusCode(401)
                .body("error", is("Usuário e/ou Senha incorretos."))
        ;
    }


    @DisplayName("E-mail e senha são obrigatórios")
    @Test
    public void testShouldRequiredCredentials() throws IOException {
        User user = new User();
        user.setEmail("");
        user.setPassword("");

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("session")
        .then()
                .statusCode(400)
                .body("error", is("E-mail e senha são obrigatórios."))
        ;
    }

}

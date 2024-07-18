package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import java.io.IOException;

import static com.e2eburguer.utils.UtilsUser.deleteUser;
import static com.e2eburguer.utils.UtilsUser.login;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

@DisplayName("Testes do endpoint /user")
public class UserTest extends BaseTest {

    private String userID;

    @DisplayName("Cadastrar usuário com sucesso")
    @Test
    public void testCreateUser() throws IOException {

        User user = UserDataFactory.createUser();

     this.userID= given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("users")
        .then()
            .statusCode(201)
            .body("name", is(user.getName()))
            .body("email", is(user.getEmail()))
            .extract()
                .path("id");

        deleteUser(userID);
    }

    @DisplayName("Não deve permitir cadastrar usuário já cadastrado")
    @Test
    public void testShouldNotRegisterDuplicate() throws IOException {
        User user = UserDataFactory.userDuplicate();

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("users")
        .then()
                .statusCode(409)
                .body("error", is("E-mail já cadastrado"));
    }

    @DisplayName("Dados obrigatórios na criação do usuário: name, email e password")
    @Test
    public void testShouldBeRequiredDataInCreateUser(){

        User user = UserDataFactory.createUser();
        user.setName("");
        user.setEmail("");
        user.setPassword("");

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("users")
        .then()
                .statusCode(400)
                .body("error", is("Nome, e-mail e senha são obrigatórios."));

    }

    @DisplayName("Buscar Detalhes do usuário")
    @Test
    public void testGetUserDetails() throws IOException {
        User user = UserDataFactory.userId();

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", user.getUserId())
        .when()
                .get("me")
        .then()
                .statusCode(200)
                .body("id", is(user.getUserId()))
                .body("name", is(user.getName()))
                .body("email", is(user.getEmail()))
        ;


    }


    //exemplo de teste de schema - contrato
   // @DisplayName("Teste de contrato")
   // @Test
  //  public void testCreateUserContract() throws IOException {

  //      User user = UserDataFactory.createUser();

  //      given()
  //              .contentType(ContentType.JSON)
  //              .body(user)
  //      .when()
  //              .post("users")
 //      .then()
  //              .statusCode(201)
  //              .body(matchesJsonSchemaInClasspath("schemas/postUserSchema.json"));
  //  }


}

package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static com.e2eburguer.utils.AuthUtils.login;
import static com.e2eburguer.utils.UserUtils.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes do endpoint /user")
public class UserTest extends BaseTest {


    @DisplayName("Cadastrar novo usuário com perfil gestão")
    @Test
    public void testRegisterGestaoUser() throws IOException {

        User user = UserDataFactory.loadUser("isGestao");

        String userID = given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(201)
                .body("name", is(user.getName()))
                .body("email", is(user.getEmail()))
                .extract()
                .path("id");

        deleteUser(userID);

    }

    @DisplayName("Cadastrar novo usuário com perfil salão")
    @Test
    public void testRegisterSalaoUser() throws IOException {

        User user = UserDataFactory.loadUser("isSalao");

        String userID = given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(201)
                .body("name", is(user.getName()))
                .body("email", is(user.getEmail()))
                .extract()
                .path("id");

        deleteUser(userID);

    }

    @DisplayName("Não deve cadastrar usuário quando e-mail já cadastrado")
    @Test
    public void testNotRegisterDuplicateEmail() throws IOException {
        User user = UserDataFactory.userIsGestao();

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(409)
                .body("error", is("E-mail já cadastrado."));
    }

    @DisplayName("Verificar os campos obrigatórios no cadastro do usuário")
    @Test
    public void testValidateRequiredFields(){

        User user = UserDataFactory.userIsGestao();
        user.setName("");
        user.setEmail("");
        user.setPassword("");
        user.setConfirmPassword("");
        user.setIsGestao(null);

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(400)
                .body("error", is("Preencha todos oc campos para prosseguir com o cadastro!"));

    }


    @DisplayName("Não deve cadastrar usuário sem preencher nome completo")
    @Test
    public void testValidateRequiredFullNameForUserRegistration(){

        User user = UserDataFactory.userIsGestao();
        user.setName("Marina");


        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(400)
                .body("error", is("Preencha com nome e sobrenome."));

    }


    @DisplayName("Validar quando senha inválida")
    @Test
    public void testInvalidPassword(){

        User user = UserDataFactory.userIsGestao();
        user.setPassword("Test1");

        String expectedMessage = "Senha inválida. A senha deve conter entre 8 e 12 caracteres, incluindo ao menos uma letra maiúscula, ao menos um número e ao menos um caractere especial.";


        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(400)
                .body("error", is(expectedMessage));

    }


    @DisplayName("Validar quando senhas não coincidem")
    @Test
    public void testPasswordMismatch(){

        User user = UserDataFactory.userIsGestao();
        user.setConfirmPassword("Teste123");

        String expectedMessage = "As senhas não coincidem.";


        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(400)
                .body("error", is(expectedMessage));

    }


    @DisplayName("Validar quando e-mail inválido")
    @Test
    public void testInvalidEmail(){

        User user = UserDataFactory.userIsGestao();
        user.setEmail("invalid.email.com");


        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(400)
                .body("error", is("Formato de e-mail inválido."));

    }


    @DisplayName("Buscar detalhes do usuário logado")
    @Test
    public void testFetchUserDetails() throws IOException {
        User user = UserDataFactory.userId();

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", user.getUserId())
        .when()
                .get("user/detail")
        .then()
                .statusCode(200)
                .body("id", is(user.getUserId()))
                .body("name", is(user.getName()))
                .body("email", is(user.getEmail()))
                .body("isGestao", is (user.getIsGestao()))
        ;

    }


    @DisplayName("Buscar lista de usuários cadastrados")
    @Test
    public void testGetUsersList() throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
        .when()
                .get("users")
        .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("id", everyItem(notNullValue()))
                .body("name", everyItem(notNullValue()))
                .body("email", everyItem(notNullValue()))
                .body("isGestao", everyItem(isA(Boolean.class)))
                .body("$", hasSize(greaterThan(0)));
    }

    @DisplayName("Verificar tentativa de busca de um usuário quando ID inexistente")
    @Test
    public void testUserNotFoundById() throws IOException {
        String userID = "123456";

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", userID)
        .when()
                .get("users/"+userID)
        .then()
                .statusCode(404)
                .body("error", is("Usuário não encontrado."));

    }



    @DisplayName("Atualizar dados de usuário")
    @Test
    public void testUpdateUserDetails() throws IOException {
        User dataUser = UserDataFactory.updateUser();
        String userId = dataUser.getUserId();

        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("name", "Maria do Carmo Atualizada");
        updatedFields.put("password", "NovaSenha@2!");
        updatedFields.put("confirmPassword", "NovaSenha@2!");
        updatedFields.put("isGestao", false);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(updatedFields)
                .pathParam("user_id", userId)
        .when()
                .put("user/{user_id}")
        .then()
                .statusCode(200)
                .body("id", is(userId))
                .body("name", is("Maria do Carmo Atualizada"))
                .body("isGestao", is(false))
                .body("email", is("m.carmo@e2ebruguer.com.br"));

        restoreUserState(userId);

    }


    @DisplayName("Excluir um usuário pelo ID")
    @Test
    public void testDeleteUser() throws IOException {
        String userID = createUser("isSalao");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", userID)
        .when()
                .delete("user/delete")
        .then()
                .statusCode(204);

    }


    @DisplayName("Tentar excluir um usuário que não foi encontrado")
    @Test
    public void testDeleteUserNotFound() throws IOException {
        String userID = "123456";

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", userID)
        .when()
                .delete("user/delete")
        .then()
                .statusCode(404)
                .body("error", is("Usuário não encontrado."));

    }

}

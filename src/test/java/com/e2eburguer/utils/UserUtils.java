package com.e2eburguer.utils;

import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;
import java.io.IOException;

import static com.e2eburguer.utils.AuthUtils.login;
import static io.restassured.RestAssured.given;



public class UserUtils {


    public static void deleteUser(String userID ) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", userID)
        .when()
                .delete("user/delete")
        .then()
                .statusCode(204);
    }



    public static String createUser(String profile) throws IOException {
        User user = UserDataFactory.loadUser(profile);

        return given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("user")
        .then()
                .statusCode(201)
                .extract()
                .path("id");
    }


    public static void restoreUserState(String userId) throws IOException {
        User user = UserDataFactory.updateUser();

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(user)
                .pathParam("user_id", userId)
        .when()
                .put("user/{user_id}")
        .then()
                .statusCode(200);

    }

}

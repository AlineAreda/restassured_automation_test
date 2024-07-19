package com.e2eburguer.utils;

import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Utils {

    public static String login() throws IOException {
        User user = UserDataFactory.login();

        return given()
                .contentType(ContentType.JSON)
                .body(user)
            .when()
                .post("session")
            .then()
                .extract()
                .path("token");

    }

    public static void deleteUser(String userID ) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("user_id", userID)
        .when()
                .delete("user")
        .then()
                .statusCode(204);
    }

    public static void deleteCategory(String categoryID ) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .queryParam("category_id", categoryID)
        .when()
                .delete("category/remove")
        .then()
                .statusCode(200);
    }
}

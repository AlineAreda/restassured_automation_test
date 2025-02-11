package com.e2eburguer.utils;

import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AuthUtils {
    private static String token;

    public static String login() throws IOException {
        if (token == null || token.isEmpty()) {
            User user = UserDataFactory.login();

            token = given()
                    .contentType(ContentType.JSON)
                    .body(user)
                    .when()
                    .post("session")
                    .then()
                    .extract()
                    .path("token");
        }
        return token;
    }
}

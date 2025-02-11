package com.e2eburguer.utils;

import io.restassured.http.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ProductUtils {
    public static void deleteProduct(String productID) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AuthUtils.login())
        .when()
                .delete("product/remove/" + productID)
        .then()
                .statusCode(204);
    }
}

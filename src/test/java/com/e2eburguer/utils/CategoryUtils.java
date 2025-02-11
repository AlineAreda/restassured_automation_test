package com.e2eburguer.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.e2eburguer.utils.AuthUtils.login;
import static io.restassured.RestAssured.given;

public class CategoryUtils {

    public static String createCategoryIfNotExists(String categoryName) throws IOException {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
        .when()
                .get("category/list");

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao listar categorias: " + response.statusCode());
        }

        List<Map<String, Object>> categories = response.jsonPath().getList("$");
        for (Map<String, Object> category : categories) {
            if (category.get("name").equals(categoryName)) {
                return category.get("id").toString();
            }
        }

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(Map.of("name", categoryName))
        .when()
                .post("category")
        .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    public static void deleteCategory(String categoryID) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .queryParam("category_id", categoryID)
        .when()
                .delete("category/remove")
        .then()
                .statusCode(200);
    }

    public static void deleteCategoryByName(String categoryName) throws IOException {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
        .when()
                .get("category/list");

        // Buscar o ID da categoria pelo nome
        List<Map<String, Object>> categories = response.jsonPath().getList("$");
        for (Map<String, Object> category : categories) {
            if (category.get("name").equals(categoryName)) {
                String categoryId = category.get("id").toString();

                deleteCategory(categoryId);
            }
        }
    }
}

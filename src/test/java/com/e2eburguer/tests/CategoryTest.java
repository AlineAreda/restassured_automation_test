package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.CategoryDataFactory;
import com.e2eburguer.pojo.Category;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.e2eburguer.utils.AuthUtils.login;
import static com.e2eburguer.utils.CategoryUtils.createCategoryIfNotExists;
import static com.e2eburguer.utils.CategoryUtils.deleteCategory;
import static com.e2eburguer.utils.UserUtils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes do endpoint /category")
public class CategoryTest extends BaseTest {

    private final List<String> createdCategoryIds = new ArrayList<>();


    @DisplayName("Cadastrar nova categoria")
    @Test
    public void testCreateNewCategory() throws IOException {
        Category category = CategoryDataFactory.newCategory();

        String categoryId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(category)
        .when()
                .post("category")
        .then()
                .statusCode(201)
                .body("name", is("Teste nova categoria"))
                .extract()
                .path("id");


        createdCategoryIds.add(categoryId);
    }

    @DisplayName("Listar Categorias")
    @Test
    public void testListAllCategories() throws IOException {
        for (String name : Arrays.asList("Bebidas", "Burguers")) {
            String categoryId = createCategoryIfNotExists(name);
            if (categoryId != null) {
                createdCategoryIds.add(categoryId);
            }
        }

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
        .when()
                .get("category/list")
        .then()
                .statusCode(200)
                .body("name", hasItems("Bebidas", "Burguers"));
    }

    @DisplayName("Nome da categoria é obrigatório.")
    @Test
    public void testCategoryNameIsMandatory() throws IOException {
        Category category = CategoryDataFactory.newCategory();
        category.setName("");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(category)
        .when()
                .post("category")
        .then()
                .statusCode(400)
                .body("error", is("Informe um nome válido para a categoria."));


    }

    @AfterEach
    public void cleanup() throws IOException {
        for (String categoryId : createdCategoryIds) {
            if (categoryId != null) {
                deleteCategory(categoryId);
            }
        }
        createdCategoryIds.clear();
    }

}

package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.pojo.Category;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.e2eburguer.utils.Utils.deleteCategory;
import static com.e2eburguer.utils.Utils.login;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DisplayName("Testes do endpoint /category")
public class CategoryTest extends BaseTest {

    private String categoryID;

    @DisplayName("Cadastrar nova categoria")
    @Test
    public void testCreateNewCategory() throws IOException {
        Category category = new Category();
        category.setName("Sobremessa");

        this.categoryID= given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .body(category)
        .when()
                .post("category")
        .then()
                .statusCode(201)
                .body("name", is("Sobremessa"))
                .extract()
                    .path("id");

        deleteCategory(categoryID);
    }

    @DisplayName("Listar Categorias")
    @Test
    public void testListAllCategories() throws IOException {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
        .when()
                .get("category/list")
        .then()
                .statusCode(200)
                .body("[0].name", equalTo("Bebidas"))
                .body("[1].name", equalTo("Burguers"));
    }

}

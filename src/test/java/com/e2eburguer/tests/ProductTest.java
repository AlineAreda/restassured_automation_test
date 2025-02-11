package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.ProductDataFactory;
import com.e2eburguer.pojo.Product;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


import static com.e2eburguer.utils.AuthUtils.login;
import static com.e2eburguer.utils.CategoryUtils.*;
import static com.e2eburguer.utils.ProductUtils.deleteProduct;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@DisplayName("Testes do endpoint /product")
public class ProductTest extends BaseTest {

    String productID;
    File file = new File("src/test/resources/files/burguer.jpeg");

    //bug aberto - erro ao cadastrar produto com imagem de banner
    @DisplayName("Cadastrar um novo produto no cardápio")
    @Test
    public void testCreateANewProduct() throws IOException {

        String categoryId = createCategoryIfNotExists("Categoria de produto");


        Product product = ProductDataFactory.newProduct();
        product.setCategoryId(categoryId);

        this.productID = given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + login())
                .multiPart("name", product.getName())
                .multiPart("price", product.getPrice())
                .multiPart("description", product.getDescription())
                .multiPart("file", file)
                .multiPart("category_id", product.getCategoryId())
        .when()
                .post("product")
        .then()
                .statusCode(201)
                .body("name", is(product.getName()))
                .body("description", is(product.getDescription()))
                .extract()
                .path("id");


    }


    @DisplayName("Não cadastrar produto com categoria inexistente")
    @Test
    public void testNotFoundCategory() throws IOException {

        Product product = ProductDataFactory.newProduct();
        product.setCategoryId("c34874b8-7764-4dbb-a751-dcf23780d4d1");

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + login())
                .multiPart("name", product.getName())
                .multiPart("price", product.getPrice())
                .multiPart("description", product.getDescription())
                .multiPart("file", file)
                .multiPart("category_id", product.getCategoryId())
        .when()
                .post("product")
        .then()
                .statusCode(404)
                .body("error", is("Categoria não encontrada"));
    }


    @DisplayName("Banner de foto é opcional no Cadastro de  produto")
    @Test
    public void testCreateProductWithoutBanner() throws IOException {

        String categoryId = createCategoryIfNotExists("Categoria de Teste");


        Product product = ProductDataFactory.newProduct();
        product.setCategoryId(categoryId);

        this.productID = given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + login())
                .multiPart("name", product.getName())
                .multiPart("price", product.getPrice())
                .multiPart("description", product.getDescription())
                .multiPart("category_id", product.getCategoryId())
        .when()
                .post("product")
        .then()
                .statusCode(201)
                .body("name", is(product.getName()))
                .body("description", is(product.getDescription()))
                .extract()
                .path("id");

    }

    @DisplayName("Falha ao cadastrar com campos obrigatórios faltantes")
    @Test
    public void testMissingRequiredFields() throws IOException {

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + login())
                .multiPart("name", "")
                .multiPart("price", "")
                .multiPart("description", "")
                .multiPart("file", "")
                .multiPart("category_id", "")
        .when()
                .post("product")
        .then()
                .statusCode(400)
                .body("error", containsString("Campos obrigatórios não preenchidos: nome, preço, descrição e categoria."));

    }

    @DisplayName("Falha ao cadastrar produto com preço inválido")
    @Test
    public void testInvalidPrice() throws IOException {
        String categoryId = createCategoryIfNotExists("Categoria de Teste");

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + login())
                .multiPart("name", "Produto Teste")
                .multiPart("price", "-10")
                .multiPart("description", "Descrição do produto")
                .multiPart("category_id", categoryId)
        .when()
                .post("product")
        .then()
                .statusCode(400)
                .body("error", containsString("Preço inválido. Deve ser um número decimal positivo."));

    }

    @AfterEach
    public void cleanup() throws IOException {
        if (productID != null) {
            deleteProduct(productID);
            productID = null;
        }
        deleteCategoryByName("Categoria de Teste");
    }

}
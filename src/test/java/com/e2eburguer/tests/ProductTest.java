package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.ProductDataFactory;
import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.Category;
import com.e2eburguer.pojo.Product;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static com.e2eburguer.utils.Utils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;

@DisplayName("Testes do endpoint /product")
public class ProductTest extends BaseTest {

    private String productID;
    File file = new File("src/test/resources/files/burguer.jpeg");

    @DisplayName("Cadastrar um novo produto no cardápio")
    @Test
    public void testCreateANewProduct() throws IOException {

        Product product = ProductDataFactory.newProduct();

        this.productID= given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization",  "Bearer " + login())
                .multiPart("name", product.getName() )
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

        deleteProduct(productID);
    }

    @DisplayName("Não cadastrar produto já cadastrado")
    @Test
    public void testProductAlreadyExists() throws IOException {

        Product product = ProductDataFactory.productAlreadyExists();

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization",  "Bearer " + login())
                .multiPart("name", product.getName() )
                .multiPart("price", product.getPrice())
                .multiPart("description", product.getDescription())
                .multiPart("file", file)
                .multiPart("category_id", product.getCategoryId())
        .when()
                .post("product")
        .then()
                .statusCode(400)
                .body("error", is("Produto já cadastrado!"));

    }

    @DisplayName("Não cadastrar produto com categoria inexistente")
    @Test
    public void testNotFoundCategory() throws IOException {

        Product product = ProductDataFactory.newProduct();

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization",  "Bearer " + login())
                .multiPart("name", product.getName() )
                .multiPart("price", product.getPrice())
                .multiPart("description", product.getDescription())
                .multiPart("file", file)
                .multiPart("category_id", "123456789")
        .when()
                .post("product")
        .then()
                .statusCode(404)
                .body("error", is("Categoria não encontrada"));

    }

    @DisplayName("Não Cadastrar produto sem foto")
    @Test
    public void testErrorUploadBanner() throws IOException {

        Product product = ProductDataFactory.newProduct();

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization",  "Bearer " + login())
                .multiPart("name", product.getName() )
                .multiPart("price", product.getPrice())
                .multiPart("description", product.getDescription())
                .multiPart("file", "")
                .multiPart("category_id", product.getCategoryId())
        .when()
                .post("product")
        .then()
                .statusCode(400)
                .body("error", is("Erro no upload da foto"));

    }

}
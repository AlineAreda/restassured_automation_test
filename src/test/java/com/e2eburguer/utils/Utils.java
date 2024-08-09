package com.e2eburguer.utils;

import com.e2eburguer.dataFactory.UserDataFactory;
import com.e2eburguer.pojo.Order;
import com.e2eburguer.pojo.User;
import io.restassured.http.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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

    public static void deleteProduct(String productID ) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
        .when()
                .delete("product/remove/" + productID)
        .then()
                .statusCode(204);
    }

    public static void deleteOrder(String orderID ) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
        .when()
                .delete("order/remove/" + orderID)
        .then()
                .statusCode(200)
                .body("message", is("Pedido removido com sucesso"));
    }

    public static String getOrderId() throws IOException {
        Order order = new Order();
        order.setTable(2);
        order.setName("Teste Order");

        String orderId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(order)
        .when()
                .post("order")
        .then()
                .statusCode(201)
                .extract()
                    .path("id");

        return orderId;
    }





}

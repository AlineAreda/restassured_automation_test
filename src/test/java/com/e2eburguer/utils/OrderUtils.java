package com.e2eburguer.utils;

import com.e2eburguer.pojo.Order;
import io.restassured.http.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class OrderUtils {
    public static String getOrderId() throws IOException {
        Order order = new Order();
        order.setTable(2);
        order.setName("Teste Order");

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AuthUtils.login())
                .body(order)
        .when()
                .post("order")
        .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    public static void deleteOrder(String orderID) throws IOException {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AuthUtils.login())
        .when()
                .delete("order/remove/" + orderID)
        .then()
                .statusCode(200)
                .body("message", is("Pedido removido com sucesso"));
    }
}

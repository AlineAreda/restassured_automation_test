package com.e2eburguer.tests;

import com.e2eburguer.config.BaseTest;
import com.e2eburguer.dataFactory.OrderDataFactory;
import com.e2eburguer.pojo.Order;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Map;

import static com.e2eburguer.dataFactory.OrderDataFactory.createAddItemPayload;
import static com.e2eburguer.utils.Utils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayName("Testes do endpoint /order")
public class OrderTest extends BaseTest {

    private String ordeID;

    @DisplayName("Abrir um novo pedido")
    @Test
    public void testCreateNewOrder() throws IOException {

        Order order = OrderDataFactory.newOrder();

        this.ordeID= given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
                .body(order)
        .when()
                .post("order")
        .then()
                .statusCode(201)
                .body("table", is(order.getTable()))
                .body("status", is(false))
                .body("draft", is(true))
                .body("name", is(order.getName()))
                .extract()
                    .path("id");

       deleteOrder(ordeID);
    }

    @DisplayName("Adicionar item ao pedido")
    @Test
    public void testAddItemToOrder() throws IOException {
        String orderId = getOrderId();

        // Criar o payload dinamicamente
        Map<String, Object> addItemPayload = createAddItemPayload(orderId);

        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + login())
                .body(addItemPayload)
        .when()
                .post("order/add")
        .then()
                .statusCode(200)
                .body("product_id", is("50c388bc-8637-4563-8adf-1167da37c1dd"))
                .body("amount", is(2));
    }

    @DisplayName("Verificar mensagem quando lista vazia")
    @Test
    public void testGetOrdersEmptyList() throws IOException {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + login())
        .when()
                .get("orders")
        .then()
                .statusCode(200)
                .log().all()
                .body("message", is("Nenhum pedido encontrado."));
    }


}

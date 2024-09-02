package com.e2eburguer.dataFactory;

import com.e2eburguer.pojo.Order;
import java.util.HashMap;
import java.util.Map;


public class OrderDataFactory {

    public static Order newOrder(){
        Order order = new Order();

        order.setTable(1);
        order.setName("Teste QA");

        return order;
    }

    public static Map<String, Object> createAddItemPayload(String orderId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("order_id", orderId);
        payload.put("product_id", "50c388bc-8637-4563-8adf-1167da37c1dd");
        payload.put("amount", 2);
        return payload;
    }



}

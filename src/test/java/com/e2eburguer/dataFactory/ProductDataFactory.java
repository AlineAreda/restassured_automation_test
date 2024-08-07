package com.e2eburguer.dataFactory;

import com.e2eburguer.pojo.Product;


public class ProductDataFactory {


    public static Product newProduct(){
        Product product = new Product();

        product.setName("Task Burguer");
        product.setPrice("35.90");
        product.setDescription("Burguer Grelhado Na Brasa! Alface, Cebola, Tomate, Picles, Molho Barbecue, Queijo Cheddar e Mussarela");
        product.setCategoryId("3de5e149-3483-445a-b5c6-3e66c9df27f5");

        return product;
    }

    public static Product productAlreadyExists(){
        Product product = new Product();

        product.setName("Java Burguer");
        product.setPrice("35");
        product.setDescription("Hambúrguer Grelhado Na Brasa. Pão, Rúcula, Cebola, Tomate, Picles, Bacon, Molho Barbecue, Queijo Cheddar Mussarela e molho do chefe");
        product.setCategoryId("3de5e149-3483-445a-b5c6-3e66c9df27f5");

        return product;
    }


}

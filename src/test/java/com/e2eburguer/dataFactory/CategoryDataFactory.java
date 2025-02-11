package com.e2eburguer.dataFactory;

import com.e2eburguer.pojo.Category;

public class CategoryDataFactory {

    public static final String DEFAULT_NAME = "Teste nova categoria";

    public static Category newCategory(){
        Category category = new Category();

       category.setName(DEFAULT_NAME);


        return category;
    }
}

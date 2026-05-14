package com.v33toolsltd.factory.business;


import com.v33toolsltd.domain.business.Category;
import com.v33toolsltd.util.Helper;

public class CategoryFactory {

    public static Category createCategory(
            String name,
            String description) {

        if (Helper.isNullOrEmpty(name))
        {return null;}

        if (Helper.isNullOrEmpty(description))
        {return null;}

        return new Category.Builder()
                .setName(name)
                .setDescription(description)
                .build();
    }
}
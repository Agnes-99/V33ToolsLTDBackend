package com.v33toolsltd.factory.business;


import com.v33toolsltd.domain.business.Brands;
import com.v33toolsltd.domain.business.Product;
import com.v33toolsltd.util.Helper;

public class ProductFactory {

    public static Product createProduct(String name,
                                        String description,
                                        String imageUrl,
                                        double price,
                                        int stockQuantity,
                                        int categoryId,
                                        Brands brand) {

        if (Helper.isNullOrEmpty(name) ||
                Helper.isNullOrEmpty(description) ||
                Helper.isNullOrEmpty(imageUrl) ||
                !Helper.isValidPrice(price) ||
                !Helper.isValidQuantity(stockQuantity) ||
                categoryId <= 0 ||
                brand == null) {
            return null;
        }

        return new Product.Builder()
                .setName(name)
                .setDescription(description)
                .setImage_url(imageUrl)
                .setPrice(price)
                .setStockQuantity(stockQuantity)
                .setCategoryId(categoryId)
                .setBrand(brand)
                .build();
    }
}


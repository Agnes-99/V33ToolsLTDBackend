package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Product;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface IProductService extends IService<Product, Long> {
    List<Product> findByCategoryId(int categoryId);
    Iterable<Product> getAll();
}


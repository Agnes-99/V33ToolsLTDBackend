package com.v33toolsltd.service.business;


import com.v33toolsltd.domain.business.Product;
import com.v33toolsltd.repository.business.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Product> getAll() {
        return repository.findAll();
    }


    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        if (product.getId() != null && repository.existsById(product.getId())) {
            return repository.save(product);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        return  repository.findByCategoryId(categoryId);
    }
}

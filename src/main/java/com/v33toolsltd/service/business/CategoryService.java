package com.v33toolsltd.service.business;


import com.v33toolsltd.domain.business.Category;
import com.v33toolsltd.repository.business.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        return repository.save(category);
    }

    @Override
    public Category read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Category update(Category category) {
        if (repository.existsById(category.getId())) {
            return repository.save(category);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Category> searchByDescription(String keyword) {
        return repository.findByDescriptionContaining(keyword);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }
}


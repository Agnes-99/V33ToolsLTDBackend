package com.v33toolsltd.controller.business;


import com.v33toolsltd.domain.business.Category;
import com.v33toolsltd.service.business.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService service;

    @Autowired
    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Category create(@RequestBody Category category) {
        return service.create(category);
    }

    @GetMapping("/read/{id}")
    public Category read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Category update(@RequestBody Category category) {
        return service.update(category);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return service.getAll();
    }

    @GetMapping("/search/name/{name}")
    public List<Category> findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping("/search/description")
    public List<Category> searchByDescription(@RequestParam String keyword) {
        return service.searchByDescription(keyword);
    }
}

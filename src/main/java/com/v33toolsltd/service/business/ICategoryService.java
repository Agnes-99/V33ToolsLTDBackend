package com.v33toolsltd.service.business;

import com.v33toolsltd.domain.business.Category;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface ICategoryService extends IService<Category,Long> {

    List<Category> getAll();
    List<Category> findByName(String name);
    List<Category> searchByDescription(String keyword);
}

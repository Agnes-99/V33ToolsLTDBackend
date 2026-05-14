package com.v33toolsltd.repository.business;

import com.v33toolsltd.domain.business.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String name);

    List<Category> findByDescriptionContaining(String keyword);
}

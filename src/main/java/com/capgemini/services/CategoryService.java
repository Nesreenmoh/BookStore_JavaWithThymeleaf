package com.capgemini.services;


import com.capgemini.domains.Category;
import com.capgemini.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public Category findById(Long id) {
        Optional<Category> returnedCategory = categoryRepository.findById(id);
        return returnedCategory.get();
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryName(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}

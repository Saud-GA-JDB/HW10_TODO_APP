package com.example.todo.service;

import com.example.todo.exception.InformationExistException;
import com.example.todo.exception.InformationNotFoundException;
import com.example.todo.model.Category;
import com.example.todo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category categoryObj) {
        Category category = categoryRepository.findByName(categoryObj.getName());
        if (category != null)
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        return categoryRepository.save(categoryObj);
    }

    public Category getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null)
            throw new InformationNotFoundException("category with id " + id + " doesn't exist");
        return category;
    }
}

package com.example.todo.controller;

import com.example.todo.model.Category;
import com.example.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("/hello")
//    public String hello() {
//        return "Hello World!";
//    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public Category addCategory(@RequestBody Category categoryObj) {
        return categoryService.addCategory(categoryObj);
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable(value = "id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long id, @RequestBody Category categoryObj) {
        return  categoryService.updateCategory(id, categoryObj);
    }

    @DeleteMapping("/{id}")
    public Category deleteCategory(@PathVariable(value = "id") Long id) {
        return categoryService.deleteCategory(id);
    }

}

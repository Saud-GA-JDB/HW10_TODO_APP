package com.example.todo.service;

import com.example.todo.exception.InformationExistException;
import com.example.todo.exception.InformationNotFoundException;
import com.example.todo.model.Category;
import com.example.todo.model.User;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public List<Category> getCategories() {
//        return categoryRepository.findAll();
        return categoryRepository.findByUserId(getCurrentLoggedInUser().getId()); // new

    }

    public Category addCategory(Category categoryObj) {
        Category category = categoryRepository.findByUserIdAndId(getCurrentLoggedInUser().getId() ,categoryObj.getId());
        if (category != null)
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        category.setUser(getCurrentLoggedInUser());
        if (category.getUser() == null)
            throw new InformationExistException("user couldn't be added - operation abandoned ");
        categoryObj.setUser(getCurrentLoggedInUser());
        return categoryRepository.save(categoryObj);
    }

    public Category getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null)
            throw new InformationNotFoundException("category with id " + id + " doesn't exist");
        return category;
    }

    public Category updateCategory(Long id, Category categoryObj) {
        Category category = categoryRepository.findById(id).orElseThrow( () -> {
            throw new InformationNotFoundException("category with id " + id + " doesn't exist");
        });
        category.setName(categoryObj.getName());
        category.setDescription(categoryObj.getDescription());

        category.setUser(getCurrentLoggedInUser()); //new

        categoryRepository.save(category);
        return category;
    }

    public Category deleteCategory(Long id) {
        Category tempCategory = categoryRepository.findById(id).orElseThrow( () -> {
            throw new InformationNotFoundException("category with id " + id + " doesn't exist");
        });
        Category category = categoryRepository.findByUserIdAndId(getCurrentLoggedInUser().getId(), tempCategory.getId());

        categoryRepository.deleteById(id);
        getCurrentLoggedInUser();
        return category;
    }
}

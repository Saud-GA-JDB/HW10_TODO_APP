package com.example.todo.repository;

import com.example.todo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
    Category findByUserIdAndId(Long userId, Long categoryId);
    List<Category> findByUserId(Long userId);
}

package com.example.todo.controller;

import com.example.todo.model.Item;
import com.example.todo.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class ItemController {
    private ItemService itemService;

    @GetMapping("{categoryId}/items")
    public List<Item> getItems(@PathVariable(value = "catedoryId") Long categoryId) {
        return itemService.getItems(categoryId);
    }

    @PostMapping("{categoryId}/items")
    public Item addItem(@PathVariable(value = "catedoryId") Long categoryId, @RequestBody Item itemObj) {
        return itemService.addItem(categoryId, itemObj);
    }

    @GetMapping("{categoryId}/items/{itemId}")
    public Item getItems(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @PutMapping("{categoryId}/items/{itemId}")
    public Item updateItem(@PathVariable(value = "itemId") Long itemId, @RequestBody Item itemObj) {
        return itemService.updateItem(itemId, itemObj);
    }

    @DeleteMapping("{categoryId}/items/{itemId}")
    public Item deleteItem(@PathVariable(value = "itemId") Long itemId) {
        return itemService.deleteItem(itemId);
    }

}

package com.example.todo.service;

import com.example.todo.exception.InformationNotFoundException;
import com.example.todo.model.Category;
import com.example.todo.model.Item;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    public List<Item> getItems(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId)
                .orElseThrow( () ->
                    new InformationNotFoundException("category with id " + categoryId + " doesn't exists")
                );
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow( () -> new InformationNotFoundException("Item with id " + itemId + " doesn't exists"));
    }

    public Item addItem(Long categoryId, Item item) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new InformationNotFoundException("category with id " + categoryId + " doesn't exists")
        );
        item.setCategory(category);

        return itemRepository.save(item);
    }

    public Item updateItem(Long itemId, Item itemObj) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new InformationNotFoundException("Item with id " + itemId + " doesn't exists")
        );
        item.setName(itemObj.getName());
        item.setDescription(itemObj.getDescription());
        item.setDueDate(itemObj.getDueDate());
        item.setCategory(itemObj.getCategory());
        return itemRepository.save(item);
    }

    public Item deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new InformationNotFoundException("Item with id " + itemId + " doesn't exists")
        );
        itemRepository.deleteById(itemId);
        return item;
    }
}

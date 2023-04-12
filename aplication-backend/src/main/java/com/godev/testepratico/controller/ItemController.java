package com.godev.testepratico.controller;

import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemEntity saveItem(@RequestBody @Valid ItemEntity itemEntity){
        return itemService.saveItem(itemEntity);
    }

    @GetMapping("/{id}")
    public ItemEntity getItemById (@PathVariable UUID id) {
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public ItemEntity updateItem(@PathVariable UUID id, @RequestBody @Valid ItemEntity newItem){
        return itemService.updateItem(id, newItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable UUID id){
        itemService.deleteItem(id);
    }

    @GetMapping
    public List<ItemEntity> getAllItems(){
        return itemService.getItems();
    }

}

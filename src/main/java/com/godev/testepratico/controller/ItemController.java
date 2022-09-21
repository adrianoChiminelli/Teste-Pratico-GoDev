package com.godev.testepratico.controller;

import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemEntity saveItem(@RequestBody ItemEntity itemEntity){
        return itemService.saveItem(itemEntity);
    }

    @GetMapping("/{id}")
    public ItemEntity getItemById (@PathVariable UUID id) {
        return itemService.getItemById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não foi encontrado!!!"));
    }

    @PutMapping("/{id}")
    public ItemEntity updateItem(@PathVariable UUID id, @RequestBody ItemEntity newItemEntityEntity){
        Optional<ItemEntity> itemOpt = itemService.getItemById(id);
        if (itemOpt.isPresent()){
            ItemEntity itemEntity = itemOpt.get();

            itemEntity.setDescription(newItemEntityEntity.getDescription());
            itemEntity.setType(newItemEntityEntity.getType());
            itemEntity.setValue(newItemEntityEntity.getValue());

            return itemService.saveItem(itemEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não foi encontrado!!!");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable UUID id){
        if (itemService.getItemById(id).isPresent()){
            itemService.deleteItem(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não foi encontrado!!!");
        }
    }

    @GetMapping
    public List<ItemEntity> getAllItems(){
        return itemService.getItems();
    }

}

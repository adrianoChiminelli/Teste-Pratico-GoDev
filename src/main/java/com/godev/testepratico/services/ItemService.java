package com.godev.testepratico.services;

<<<<<<< HEAD
import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
=======
import com.godev.testepratico.exception.ItemNotFoundException;
import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
>>>>>>> Nova-Implementacao

import java.util.List;
import java.util.Optional;
import java.util.UUID;

<<<<<<< HEAD
@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;
=======
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
>>>>>>> Nova-Implementacao

    public ItemEntity saveItem(ItemEntity itemEntity){
        return itemRepository.save(itemEntity);
    }

<<<<<<< HEAD
    public Optional<ItemEntity> getItemById(UUID id){
        return itemRepository.findById(id);
    }

    public void deleteItem(UUID id){
        itemRepository.deleteById(id);
=======
    public ItemEntity updateItem(UUID id, ItemEntity newItem){
        ItemEntity item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado!!!"));

        item.setDescription(newItem.getDescription());
        item.setType(newItem.getType());
        item.setValue(newItem.getValue());
        return itemRepository.save(item);
    }

    public ItemEntity getItemById(UUID id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado!!!"));
    }

    public void deleteItem(UUID id){
        itemRepository.findById(id)
                        .ifPresentOrElse(item -> itemRepository.deleteById(id),
                                () -> new ItemNotFoundException("Item não foi encontrado!!!"));
>>>>>>> Nova-Implementacao
    }

    public List<ItemEntity> getItems(){
        return itemRepository.findAll();
    }


}

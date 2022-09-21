package com.godev.testepratico.services;

import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public ItemEntity saveItem(ItemEntity itemEntity){
        return itemRepository.save(itemEntity);
    }

    public Optional<ItemEntity> getItemById(UUID id){
        return itemRepository.findById(id);
    }

    public void deleteItem(UUID id){
        itemRepository.deleteById(id);
    }

    public List<ItemEntity> getItems(){
        return itemRepository.findAll();
    }


}

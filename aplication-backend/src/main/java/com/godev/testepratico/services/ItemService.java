package com.godev.testepratico.services;

import com.godev.testepratico.exception.ItemNotFoundException;
import com.godev.testepratico.model.ItemEntity;
import com.godev.testepratico.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;


    public ItemEntity saveItem(ItemEntity itemEntity) {
        return itemRepository.save(itemEntity);
    }

    public ItemEntity updateItem (UUID id, ItemEntity newItem){
        ItemEntity item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado!!!"));

        item.setDescription(newItem.getDescription());
        item.setType(newItem.getType());
        item.setValue(newItem.getValue());
        return itemRepository.save(item);
    }

    public ItemEntity getItemById (UUID id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item não foi encontrado!!!"));
    }

    public void deleteItem (UUID id){
        itemRepository.deleteById(id);
    }

    public List<ItemEntity> getItems () {
        return itemRepository.findAll();
    }
}
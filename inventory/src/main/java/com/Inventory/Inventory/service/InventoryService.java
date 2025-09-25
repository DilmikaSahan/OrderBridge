package com.Inventory.Inventory.service;

import com.Inventory.Inventory.dto.InventoryDto;
import com.Inventory.Inventory.model.Inventory;
import com.Inventory.Inventory.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDto> getAllItems(){
        List<Inventory> itemList = inventoryRepo.findAll();
        return modelMapper.map(itemList,new TypeToken<List<InventoryDto>>(){}.getType());
    }

    public InventoryDto saveItem(InventoryDto inventoryDto){
        inventoryRepo.save(modelMapper.map(inventoryDto,Inventory.class));
        return inventoryDto;
    }

    public InventoryDto updateItem(InventoryDto inventoryDto){
        inventoryRepo.save(modelMapper.map(inventoryDto,Inventory.class));
        return inventoryDto;
    }

    public String deleteItem(Integer itemId){
        inventoryRepo.deleteById(itemId);
        return "Item Deleted";
    }

    public InventoryDto getItemByID(Integer itemId){
        Inventory item = inventoryRepo.getItemById(itemId);
        return modelMapper.map(item,InventoryDto.class);
    }

}


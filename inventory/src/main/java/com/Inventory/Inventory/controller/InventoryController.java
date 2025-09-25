package com.Inventory.Inventory.controller;

import com.Inventory.Inventory.dto.InventoryDto;
import com.Inventory.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getitems")
    public List<InventoryDto> getAllItems(){
        return inventoryService.getAllItems();
    }
    @GetMapping("/item/{itemId}")
    public InventoryDto getItemById(@PathVariable Integer itemId){
        return inventoryService.getItemByID(itemId);
    }

    @PostMapping("/additem")
    public InventoryDto saveItem(@RequestBody InventoryDto inventoryDto){
        return inventoryService.saveItem(inventoryDto);
    }

    @PutMapping("/updateitem")
    public InventoryDto updateItem(@RequestBody InventoryDto inventoryDto){
        return inventoryService.updateItem(inventoryDto);
    }
    @DeleteMapping("/deleteitem/{itemId}")
    public String deleteItem(@PathVariable Integer itemId){
        return inventoryService.deleteItem(itemId);
    }
}

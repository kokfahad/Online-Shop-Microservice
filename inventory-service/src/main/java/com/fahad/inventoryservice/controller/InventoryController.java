package com.fahad.inventoryservice.controller;

import com.fahad.inventoryservice.dto.InventoryResponse;
import com.fahad.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("")
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}

package com.fahad.productservice.controller;

import com.fahad.productservice.dto.ProductRequest;
import com.fahad.productservice.dto.ProductResponse;
import com.fahad.productservice.model.Product;
import com.fahad.productservice.service.ProductService;
import com.fahad.productservice.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping("/get")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }
}

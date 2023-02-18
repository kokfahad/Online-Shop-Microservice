package com.fahad.productservice.service;

import com.fahad.productservice.dto.ProductRequest;
import com.fahad.productservice.dto.ProductResponse;
import com.fahad.productservice.model.Product;

import java.util.List;

public interface ProductService {


    void createProduct(ProductRequest productRequest);

    List<Product> getAllProducts();
}

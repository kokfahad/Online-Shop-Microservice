package com.fahad.productservice.service.impl;

import com.fahad.productservice.dto.ProductRequest;
import com.fahad.productservice.dto.ProductResponse;
import com.fahad.productservice.model.Product;
import com.fahad.productservice.repository.ProductRepository;
import com.fahad.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);

        log.info("Product {} is saved", product.getId());
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }
}

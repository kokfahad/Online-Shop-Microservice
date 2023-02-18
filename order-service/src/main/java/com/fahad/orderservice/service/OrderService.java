package com.fahad.orderservice.service;

import com.fahad.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}

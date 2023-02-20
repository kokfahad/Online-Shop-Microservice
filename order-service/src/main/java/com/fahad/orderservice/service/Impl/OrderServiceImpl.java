package com.fahad.orderservice.service.Impl;

import com.fahad.orderservice.dto.InventoryResponse;
import com.fahad.orderservice.dto.OrderLineItemsDto;
import com.fahad.orderservice.dto.OrderRequest;
import com.fahad.orderservice.model.Order;
import com.fahad.orderservice.model.OrderLineItems;
import com.fahad.orderservice.repository.OrderRepository;
import com.fahad.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes =order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());
        //call inventory service, and place order if product is in stock
        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                        .block();

        boolean  allProductInStock = Arrays.stream(inventoryResponsesArray).
                allMatch(InventoryResponse::isInStock);

        if (allProductInStock){
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later !!");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}

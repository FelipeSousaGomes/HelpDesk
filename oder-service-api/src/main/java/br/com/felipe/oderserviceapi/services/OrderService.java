package br.com.felipe.oderserviceapi.services;

import br.com.felipe.oderserviceapi.entities.Order;
import models.request.CreateOrderRequest;
import models.request.UpdateOrderRequest;
import models.responses.OrderResponse;

import java.util.List;

public interface OrderService {

    Order findById(Long id);

    void save(CreateOrderRequest request);

    OrderResponse update(Long id, UpdateOrderRequest request);

    void deleteById(Long id);

    List<Order> findAll();
}

package br.com.felipe.oderserviceapi.services;

import models.request.CreateOrderRequest;

public interface OrderService {

    void save(CreateOrderRequest request);
}

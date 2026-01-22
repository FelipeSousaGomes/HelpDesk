package br.com.felipe.oderserviceapi.services.impl;

import br.com.felipe.oderserviceapi.mapper.OrderMapper;
import br.com.felipe.oderserviceapi.repositories.OrderRepository;
import br.com.felipe.oderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.request.CreateOrderRequest;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    public void save(CreateOrderRequest request) {

        orderRepository.save(mapper.fromRequest(request));
    }
}

package br.com.felipe.oderserviceapi.controller.impl;

import br.com.felipe.oderserviceapi.controller.OrderController;
import br.com.felipe.oderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.request.CreateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED).build();
    }
}

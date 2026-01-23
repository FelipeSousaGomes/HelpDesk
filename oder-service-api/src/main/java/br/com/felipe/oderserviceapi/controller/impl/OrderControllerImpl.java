package br.com.felipe.oderserviceapi.controller.impl;

import br.com.felipe.oderserviceapi.controller.OrderController;
import br.com.felipe.oderserviceapi.mapper.OrderMapper;
import br.com.felipe.oderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.request.CreateOrderRequest;
import models.request.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;
    private final OrderMapper mapper;

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok().body(mapper.fromEntity(service.findById(id)));
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        service.deleteById(id);
      return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(mapper.fromEntities(service.findAll()));
    }
}

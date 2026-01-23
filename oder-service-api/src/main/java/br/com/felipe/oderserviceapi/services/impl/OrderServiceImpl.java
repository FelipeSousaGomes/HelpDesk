package br.com.felipe.oderserviceapi.services.impl;

import br.com.felipe.oderserviceapi.clients.UserServiceFeingClient;
import br.com.felipe.oderserviceapi.entities.Order;
import br.com.felipe.oderserviceapi.mapper.OrderMapper;
import br.com.felipe.oderserviceapi.repositories.OrderRepository;
import br.com.felipe.oderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.request.CreateOrderRequest;

import models.request.UpdateOrderRequest;
import models.responses.OrderResponse;
import models.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final UserServiceFeingClient userServiceFeingClient;

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public void save(CreateOrderRequest request) {
     final var requester=  validateUserId(request.requesterId());
     final var customer=  validateUserId(request.customerId());

     log.info("Requester: {}", requester);
     log.info("Customer: {}", customer);

        final var entity = orderRepository.save(mapper.fromRequest(request));
        log.info("Order: {}", entity);
    }

    @Override
    public OrderResponse update(Long id, UpdateOrderRequest request) {
        Order entity = findById(id);
        entity = mapper.fromRequest(entity,request);

        if (entity.getStatus().equals( OrderStatusEnum.CLOSED)) {
        entity.setClosedAt(LocalDateTime.now());
        }

        return mapper.fromEntity(orderRepository.save(entity));


    }

    @Override
    public void deleteById(final Long id) {
        orderRepository.delete(findById(id));

    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        );

        return orderRepository.findAll(pageRequest);
    }


    UserResponse validateUserId(final String userId){
       return userServiceFeingClient.findById(userId).getBody();
    }


}

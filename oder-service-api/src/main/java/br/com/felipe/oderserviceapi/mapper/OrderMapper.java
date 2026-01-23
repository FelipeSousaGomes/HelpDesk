package br.com.felipe.oderserviceapi.mapper;


import br.com.felipe.oderserviceapi.entities.Order;
import models.enums.OrderStatusEnum;
import models.request.CreateOrderRequest;
import models.request.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface OrderMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status" , source = "status", qualifiedByName = "mapStatus")
    @Mapping(target = "createdAt", expression = "java(mapCreatedAt())")
    Order fromRequest(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status" , source = "status", qualifiedByName = "mapStatus")
    @Mapping(target = "createdAt", ignore = true)
    Order fromRequest(@MappingTarget Order entity , UpdateOrderRequest request);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }


    default LocalDateTime mapCreatedAt() {
        return LocalDateTime.now();
    }

    OrderResponse fromEntity(Order entity);
}
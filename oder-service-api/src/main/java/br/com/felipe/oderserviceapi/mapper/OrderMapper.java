package br.com.felipe.oderserviceapi.mapper;


import br.com.felipe.oderserviceapi.entities.Order;
import models.enums.OrderStatusEnum;
import models.request.CreateOrderRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface OrderMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status" , source = "status", qualifiedByName = "mapStatus")
    Order fromRequest(CreateOrderRequest request);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }

}
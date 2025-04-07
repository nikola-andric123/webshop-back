package fr.codecake.com.service.order;

import fr.codecake.com.dto.OrderDto;
import fr.codecake.com.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);

    List<OrderDto> getAllOrders();

    OrderDto updateOrder(OrderDto orderDto);
}

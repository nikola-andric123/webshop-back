package fr.codecake.com.dto;

import fr.codecake.com.enums.OrderStatus;
import fr.codecake.com.model.OrderItem;
import fr.codecake.com.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private Long userId;
    private String orderStatus;
    private Set<OrderItemDto> orderItems = new HashSet<>();

}

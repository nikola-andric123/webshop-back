package fr.codecake.com.dto;

import fr.codecake.com.model.Order;
import fr.codecake.com.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private int quantity;
    private String productName;
    private String productBrand;
    private BigDecimal price;
}

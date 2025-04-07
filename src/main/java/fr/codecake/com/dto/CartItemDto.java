package fr.codecake.com.dto;

import fr.codecake.com.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Long cartId;
}

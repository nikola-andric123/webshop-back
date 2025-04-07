package fr.codecake.com.repository;

import fr.codecake.com.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteCartItemsByCartId(Long id);

    List<CartItem> findAllByCartId(Long cart_id);
}

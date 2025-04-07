package fr.codecake.com.repository;

import fr.codecake.com.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getCartByUserId(Long userId);
}

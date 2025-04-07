package fr.codecake.com.repository;

import fr.codecake.com.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getOrdersByUserId(Long user_id);
}

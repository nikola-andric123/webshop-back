package fr.codecake.com.service.order;

import fr.codecake.com.dto.OrderDto;
import fr.codecake.com.enums.OrderStatus;
import fr.codecake.com.exceptions.ResourceNotFoundException;
import fr.codecake.com.model.*;
import fr.codecake.com.repository.OrderRepository;
import fr.codecake.com.repository.ProductRepository;
import fr.codecake.com.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalPrice(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        order.setUser(cart.getUser());
        return order;

    }
    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice(),
                    product,
                    order

            );
        }).toList();
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems){
        return orderItems.stream().map(item -> item.getPrice()
                .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
    @Override
    public List<OrderDto> getUserOrders(Long userId){

        return orderRepository.getOrdersByUserId(userId).stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
    public Order convertFromDto(OrderDto dto){
        return modelMapper.map(dto, Order.class);
    }

    @Override
    public List<OrderDto> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto){
        Order order = orderRepository.findById(orderDto.getOrderId()).get();
        order.setOrderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
        return convertToDto(orderRepository.save(order));
    }
}

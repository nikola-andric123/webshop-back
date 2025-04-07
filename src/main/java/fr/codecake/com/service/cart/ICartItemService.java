package fr.codecake.com.service.cart;

import fr.codecake.com.dto.CartItemDto;
import fr.codecake.com.model.CartItem;

import java.util.List;

public interface ICartItemService {
    List<CartItem> getAllCartItems(Long cartId);

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);


    CartItem getCartItem(Long cartId, Long productId);

    List<CartItemDto> getConvertedItems(List<CartItem> cartItems);
}

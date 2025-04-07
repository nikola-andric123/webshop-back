package fr.codecake.com.service.cart;

import fr.codecake.com.dto.CartItemDto;
import fr.codecake.com.dto.ProductDto;
import fr.codecake.com.exceptions.ResourceNotFoundException;
import fr.codecake.com.model.Cart;
import fr.codecake.com.model.CartItem;
import fr.codecake.com.model.Product;
import fr.codecake.com.repository.CartItemRepository;
import fr.codecake.com.repository.CartRepository;
import fr.codecake.com.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CartItem> getAllCartItems(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);

        }else {

            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);

        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        //Product product = productService.getProductById(productId);
        CartItem cartItem = getCartItem(cartId, productId);
        cart.removeItem(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        cartItem.setQuantity(quantity);
        //cartItem.setUnitPrice(productService.getProductById(productId).getPrice());
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
    @Override
    public List<CartItemDto> getConvertedItems(List<CartItem> cartItems) {
        return cartItems.stream().map(this::convertItemToDto).toList();
    }

    private CartItemDto convertItemToDto(CartItem cartItem) {
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        ProductDto productDto = modelMapper.map(cartItem.getProduct(), ProductDto.class);
        cartItemDto.setProduct(productDto);
        return cartItemDto;
    }
}

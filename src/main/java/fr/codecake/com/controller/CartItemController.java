package fr.codecake.com.controller;

import fr.codecake.com.dto.CartItemDto;
import fr.codecake.com.exceptions.ResourceNotFoundException;
import fr.codecake.com.model.Cart;
import fr.codecake.com.model.CartItem;
import fr.codecake.com.model.User;
import fr.codecake.com.response.ApiResponse;
import fr.codecake.com.service.cart.CartItemService;
import fr.codecake.com.service.cart.ICartItemService;
import fr.codecake.com.service.cart.ICartService;
import fr.codecake.com.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @GetMapping("/item/getAll")
    public ResponseEntity<ApiResponse> getAllCartItems(){
        try{
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);
            List<CartItem> allItems = cartItemService.getAllCartItems(cart.getId());
            List<CartItemDto> allItemsDto = cartItemService.getConvertedItems(allItems);
            return ResponseEntity.ok(new ApiResponse("Success", allItemsDto));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam int quantity) {
        try{
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(),productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Success",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found",null));
        }catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try{
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Success",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found",null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update-quantity")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        try{
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Success",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found",null));
        }
    }
}

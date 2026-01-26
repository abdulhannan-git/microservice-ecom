package ecommerce.order.services;


import ecommerce.order.clients.ProductServiceClient;
import ecommerce.order.clients.UserServiceClient;
import ecommerce.order.dtos.CartItemRequest;
import ecommerce.order.dtos.ProductResponse;
import ecommerce.order.dtos.UserResponse;
import ecommerce.order.models.CartItem;
import ecommerce.order.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    //    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public boolean addItem(String userId, CartItemRequest cartItemRequest) {
        ProductResponse productResponse = productServiceClient.getProductDetails(String.valueOf(cartItemRequest.getProductId()));
        if (productResponse == null) return false;

        if (productResponse.getStockQuantity() < cartItemRequest.getQuantity()) return false;

//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        if (userResponse == null) return false;
//        if (userOpt.isEmpty()) return false;
//        User user = userOpt.get();

        CartItem existingCartItem = cartRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000));
            cartRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setPrice(BigDecimal.valueOf(1000));
            cartRepository.save(cartItem);
        }
        return true;
    }

    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findByUserId(userId);
    }


    public boolean deleteItemFromCart(String userId, Long productId) {
//        Optional<Product> productOpt = productRepository.findById(productId);
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        CartItem cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}

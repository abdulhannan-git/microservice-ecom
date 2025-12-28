package ecommerce.order.repository;


import ecommerce.order.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(String userId, Long productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserIdAndProductId(String userId, String productId);

    void deleteByUserId(String userId);
}

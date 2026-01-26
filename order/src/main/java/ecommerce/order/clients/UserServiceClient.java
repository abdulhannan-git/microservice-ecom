package ecommerce.order.clients;

import ecommerce.order.dtos.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/users/{id}")
public interface UserServiceClient {

    @GetExchange("/api/users/{id}")
    UserResponse getUserDetails(@PathVariable String id);

}

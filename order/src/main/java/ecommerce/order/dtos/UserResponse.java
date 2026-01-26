package ecommerce.order.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ecommerce.order.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "firstName", "lastName", "email", "phone", "role", "addressDTO"})
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    @JsonProperty("address")
    private AddressDTO addressDTO;
}

package ecommerce.user.services;


import ecommerce.user.dto.AddressDTO;
import ecommerce.user.dto.UserRequest;
import ecommerce.user.dto.UserResponse;
import ecommerce.user.model.Address;
import ecommerce.user.model.User;
import ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void addUser(UserRequest userRequest) {
        User newUser = new User();
        mapToUserFromUserRequest(newUser, userRequest);
        userRepository.save(newUser);
    }

    private void mapToUserFromUserRequest(User newUser, UserRequest userRequest) {
        Address address = userRequest.getAddress() == null ? null :
                new Address(
                        userRequest.getAddress().getStreet(),
                        userRequest.getAddress().getCity(),
                        userRequest.getAddress().getState(),
                        userRequest.getAddress().getCountry(),
                        userRequest.getAddress().getPostalCode()
                );
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPhone(userRequest.getPhone());
        newUser.setAddress(address);
    }

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserResponseFromUser).collect(Collectors.toList());
    }

    private UserResponse mapToUserResponseFromUser(User user) {
        AddressDTO addressDTO = user.getAddress() == null ? null :
                new AddressDTO(
                        user.getAddress().getStreet(),
                        user.getAddress().getCity(),
                        user.getAddress().getState(),
                        user.getAddress().getCountry(),
                        user.getAddress().getPostalCode()
                );
        return new UserResponse(
                String.valueOf(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                addressDTO
        );
    }

    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(String.valueOf(id)).map(this::mapToUserResponseFromUser);
    }

    public boolean updateUser(String id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(user -> {
                    mapToUserFromUserRequest(user, userRequest);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }
}

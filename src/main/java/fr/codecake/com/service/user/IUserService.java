package fr.codecake.com.service.user;

import fr.codecake.com.dto.UserDto;
import fr.codecake.com.model.User;
import fr.codecake.com.request.CreateUserRequest;
import fr.codecake.com.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);

    User getUserByEmail(String email);

    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}

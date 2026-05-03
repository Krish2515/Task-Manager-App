package in.codegram.taskmgrapp.service;

import in.codegram.taskmgrapp.dto.UserDto;
import in.codegram.taskmgrapp.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    List<User> getAllUsers();

    void deleteUserById(Long id);
}

package com.basic.authentication.services;

import com.basic.authentication.models.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDTO createNewUser(UserDTO userDTO);
    void updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    Page<UserDTO> findAllUsers(Pageable pageable);
}
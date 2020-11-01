package com.basic.authentication.resources.converter;

import com.basic.authentication.config.PasswordConfig;
import com.basic.authentication.models.dtos.UserDTO;
import com.basic.authentication.models.entites.User;
import org.springframework.stereotype.Component;

@Component
public class ConverterImpl implements Converter {

    private final PasswordConfig passwordConfig;

    public ConverterImpl(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserDTO convertToDTO(User userEntity) {
        return new UserDTO
                .Builder()
                .email(userEntity.getEmail())
                .password(userEntity.getEmail())
                .isAdmin(userEntity.isAdmin())
                .build();
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        return new User
                .Builder()
                .email(userDTO.getEmail())
                .password((passwordConfig.passwordEncoder().encode(userDTO.getPassword())))
                .isAdmin(userDTO.isAdmin())
                .build();
    }

}
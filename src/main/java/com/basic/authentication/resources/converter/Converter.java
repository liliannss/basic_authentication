package com.basic.authentication.resources.converter;

import com.basic.authentication.models.dtos.UserDTO;
import com.basic.authentication.models.entites.User;

public interface Converter {

    UserDTO convertToDTO(User entity);
    User convertToEntity(UserDTO dto);

}
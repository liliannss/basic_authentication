package com.basic.authentication.services;

import com.basic.authentication.config.PasswordConfig;
import com.basic.authentication.exceptions.BusinessException;
import com.basic.authentication.models.dtos.UserDTO;
import com.basic.authentication.models.entites.User;
import com.basic.authentication.repositories.UserRepository;
import com.basic.authentication.resources.converter.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final Converter converter;
    private final PasswordConfig passwordConfig;

    private UserServiceImpl(UserRepository userRepository, Converter converter, PasswordConfig passwordConfig) {
        this.repository = userRepository;
        this.converter = converter;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        passwordConfig.passwordEncoder().encode(userDTO.getPassword());

        User userEntity = converter.convertToEntity(userDTO);

        User entitySave = repository.save(userEntity);

        return converter.convertToDTO(entitySave);
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        findById(id);

        User userEntity = converter.convertToEntity(userDTO);

        repository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        findById(id);

        repository.deleteById(id);
    }

    @Override
    public Page<UserDTO> findAllUsers(Pageable pageable) {
        List<UserDTO> userDTOList = repository.findAll(pageable)
                .stream()
                .map(converter::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(userDTOList);
    }

    private User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Objeto não localizado"));
    }

}

@Component
class FirstUser implements CommandLineRunner {

    @Value("${credentials.admin}")
    private String admin;

    @Value("${credentials.user}")
    private String user;

    @Value("${credentials.password}")
    private String password;

    private final UserRepository repository;
    private final PasswordConfig passwordConfig;

    @Deprecated
    public FirstUser(UserRepository repository, PasswordConfig passwordConfig) {
        this.repository = repository;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public void run(String... args) {
        List<User> userEntityList = Arrays.asList(
                new User
                        .Builder()
                        .email(admin)
                        .password(passwordConfig.passwordEncoder().encode(password))
                        .isAdmin(true)
                        .build(),
                new User
                        .Builder()
                        .email(user)
                        .password(passwordConfig.passwordEncoder().encode(password))
                        .isAdmin(false)
                        .build()
        );

        createUserIfNotExist(userEntityList);
    }

    private void createUserIfNotExist(List<User> userEntityList) {
        userEntityList.forEach(
                user -> {
                    Optional<User> userList = repository.findByEmail(user.getEmail());
                    if (!userList.isPresent()) {
                        repository.saveAll(userEntityList);
                    }
                }
        );
    }

}
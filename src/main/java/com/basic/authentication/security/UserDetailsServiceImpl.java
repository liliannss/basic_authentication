package com.basic.authentication.security;

import com.basic.authentication.models.entites.User;
import com.basic.authentication.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User optionalUserEntity = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));

        String[] roles = optionalUserEntity.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(optionalUserEntity.getEmail())
                .password(optionalUserEntity.getPassword())
                .roles(roles)
                .build();
    }

}
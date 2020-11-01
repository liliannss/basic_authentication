package com.basic.authentication.config;

import com.basic.authentication.security.UserDetailsServiceImpl;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl service;
    private final PasswordConfig passwordConfig;

    public SecurityConfig(UserDetailsServiceImpl service, PasswordConfig passwordConfig) {
        this.service = service;
        this.passwordConfig = passwordConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
                .passwordEncoder(passwordConfig.passwordEncoder());
    }

    String[] USERS = new String[]{"/api/users/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USERS)
                .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, USERS)
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, USERS)
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, USERS)
                .hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/api/h2-console/**",
                "/api/v2/api-docs",
                "/api/configuration/ui",
                "/api/swagger-resources/**",
                "/api/configuration/security",
                "/api/swagger-ui",
                "/api/webjars/**");
    }

}
package com.basic.authentication.models.dtos;

import java.util.Objects;

public class UserDTO {

    private String password;
    private String email;
    private boolean isAdmin;

    @Deprecated
    public UserDTO() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public static class Builder {

        private String password;
        private String email;
        private boolean isAdmin;

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }

    private UserDTO(Builder builder) {
        password = builder.password;
        email = builder.email;
        isAdmin = builder.isAdmin;
    }

}
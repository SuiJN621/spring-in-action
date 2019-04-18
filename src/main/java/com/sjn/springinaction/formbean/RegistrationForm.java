package com.sjn.springinaction.formbean;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sjn.springinaction.entity.User;

import lombok.Data;

/**
 * @author Sui
 * @date 2019.04.18 10:03
 */
@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
    }
}

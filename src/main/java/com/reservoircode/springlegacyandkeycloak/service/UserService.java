package com.reservoircode.springlegacyandkeycloak.service;

import com.reservoircode.springlegacyandkeycloak.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUser(String id) {
        return new User("1", "Ulrich");
    }
}

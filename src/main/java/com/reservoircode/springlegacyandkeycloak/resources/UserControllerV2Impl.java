package com.reservoircode.springlegacyandkeycloak.resources;

import com.reservoircode.springlegacyandkeycloak.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v2/user")
public class UserControllerV2Impl {

    @GetMapping
    public ResponseEntity<User> getUser(String id) {
        return ResponseEntity
                .ok(new User("1", "Ulrich"));
    }
}

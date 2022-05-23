package com.reservoircode.springlegacyandkeycloak.resources;

import com.reservoircode.springlegacyandkeycloak.model.User;
import com.reservoircode.springlegacyandkeycloak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v2/user")
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(String id) {
        return ResponseEntity
                .ok(userService.getUser(id));
    }
}

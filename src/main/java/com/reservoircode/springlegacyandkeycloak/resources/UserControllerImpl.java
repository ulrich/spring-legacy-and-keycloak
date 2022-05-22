package com.reservoircode.springlegacyandkeycloak.resources;

import com.reservoircode.springlegacyandkeycloak.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserControllerImpl {

    @GetMapping
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String authorization, String id) {
        var tokenOpt = Optional.ofNullable(authorization);

        if (tokenOpt.isEmpty()) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .build();
        }
        var token = tokenOpt.get();

        if (!"authorization".equals(token)) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .build();
        }
        return ResponseEntity
                .ok(new User("1", "Ulrich"));
    }
}

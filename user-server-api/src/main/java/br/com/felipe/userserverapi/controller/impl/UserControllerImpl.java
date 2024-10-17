package br.com.felipe.userserverapi.controller.impl;

import br.com.felipe.userserverapi.controller.UserController;
import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.repository.UserRepository;
import br.com.felipe.userserverapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<User> findById(String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }
}

package br.com.felipe.userserverapi.controller.impl;

import br.com.felipe.userserverapi.controller.UserController;
import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.repository.UserRepository;
import br.com.felipe.userserverapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import models.request.CreateUserRequest;
import models.request.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> findById(String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @Override
    public ResponseEntity<Void> save( @Valid  CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Override
    public ResponseEntity<UserResponse> update(final String id, final UpdateUserRequest updateUserRequest) {

        return ResponseEntity.ok().body(userService.update(id, updateUserRequest));
    }
}

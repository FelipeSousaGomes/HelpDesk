package br.com.felipe.userserverapi.service;

import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.mapper.UserMapper;
import br.com.felipe.userserverapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import models.exceptions.ResourceNotFoundException;
import models.request.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object não encontrado id: " + id + ", Type " +
                        UserResponse.class.getSimpleName())) );
       // return userRepository.findById(id).orElse(null)
    }

    public void save(CreateUserRequest createUserRequest) {
            userRepository.save(userMapper.fromRequest(createUserRequest));

    }






}

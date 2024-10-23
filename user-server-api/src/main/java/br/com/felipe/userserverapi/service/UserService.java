package br.com.felipe.userserverapi.service;

import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.mapper.UserMapper;
import br.com.felipe.userserverapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id).orElse(null));
       // return userRepository.findById(id).orElse(null)
    }





}

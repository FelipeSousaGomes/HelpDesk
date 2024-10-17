package br.com.felipe.userserverapi.service;

import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(final String id) {

        return userRepository.findById(id).orElse(null);
    }





}

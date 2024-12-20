package br.com.felipe.userserverapi.service;

import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.mapper.UserMapper;
import br.com.felipe.userserverapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import models.exceptions.ResourceNotFoundException;
import models.request.CreateUserRequest;
import models.request.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                find(id));
       // return userRepository.findById(id).orElse(null)
    }

    public void save(CreateUserRequest createUserRequest) {
                verifyIfEmailAlreadyExists(createUserRequest.email(), null);
            userRepository.save(userMapper.fromRequest(createUserRequest)
                    .withPassword(bCryptPasswordEncoder.encode(createUserRequest.password())));

    }


    public List<UserResponse> findAll() {
        return  userRepository.findAll().stream().map(x -> userMapper.fromEntity(x)).toList();




    }

    public UserResponse update(final String id,final  UpdateUserRequest updateUserRequest) {
            User entity = find(id);
            verifyIfEmailAlreadyExists(updateUserRequest.email(),id);
            return userMapper.fromEntity(userRepository.save(userMapper.update(updateUserRequest, entity)
                            .withPassword(updateUserRequest.password() != null ? bCryptPasswordEncoder.encode(updateUserRequest.password())
                                  : entity.getPassword()
                                    )
                    )

            );

    }

    private User find(final String id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object não encontrado id: " + id + ", Type " +
                UserResponse.class.getSimpleName())) ;
    }

    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
                });
    }



}

package br.com.felipe.userserverapi.service;

import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.mapper.UserMapper;
import br.com.felipe.userserverapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.request.CreateUserRequest;
import models.request.UpdateUserRequest;
import models.responses.UserResponse;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.felipe.userserverapi.creator.CreatorUtils.generatedMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService  service;

    @Mock
    private UserRepository repository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserMapper mapper;




    @Test
    void whenCallFindByIdValidReturnUserResponse() {


        when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generatedMock(UserResponse.class));


        final var response = service.findById("1");

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());
        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(User.class));
    }


    @Test
    void whenCallFindByIdInvalidThenResourceNotFoundException() {

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try{
            service.findById("1");
        } catch (Exception e){
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }

        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(0)).fromEntity(any(User.class));


     }


     @Test
    void WhenCallFindAllValidReturnUserResponse() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generatedMock(UserResponse.class));

        final var response = service.findAll();

        assertNotNull(response);
        assertEquals(UserResponse.class, response.get(0).getClass());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).fromEntity(any(User.class));
}

     @Test
    void whenCallSaveThenSuccess() {
    when(mapper.fromRequest(any())).thenReturn(new User());
    when(repository.save(any(User.class))).thenReturn(generatedMock(User.class));
    when(encoder.encode(anyString())).thenReturn("encoded");

    when(repository.findByEmail(anyString())).thenReturn(Optional.empty());


    final var request = generatedMock(CreateUserRequest.class);

    service.save(request);
        verify(mapper).fromRequest(request);
        verify(encoder, times(1)).encode(request.password());
        verify(repository, times(1)).save(any(User.class));
        verify(repository, times(1)).findByEmail(request.email());

     }

     @Test
    void WhenCallSaveInvalidEmailThenThrowException() {
        final var request = generatedMock(CreateUserRequest.class);
        final var entity = generatedMock(User.class);

        when(repository.findByEmail(request.email())).thenReturn(Optional.of(entity));

        try {
            service.save(request);
        }catch(DataIntegrityViolationException e){
            assertEquals(DataIntegrityViolationException.class, e.getClass());
        }
        verify(repository, times(1)).findByEmail(request.email());

    }

    @Test
    void whenCallUpdateWithInvalidIdThenThrowResourceNotFoundException() {
        final var request = generatedMock(UpdateUserRequest.class);

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.update("1", request);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
        }

        verify(repository).findById(anyString());
        verify(mapper, times(0)).update(any(), any());
        verify(encoder, times(0)).encode(request.password());
        verify(repository, times(0)).save(any(User.class));
    }

    @Test
    void whenCallUpdateWithInvalidEmailThenThrowDataIntegrityViolationException() {
        final var request = generatedMock(UpdateUserRequest.class);
        final var entity = generatedMock(User.class);

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        try {
            service.update("1", request);
        } catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Email [ " + request.email() + " ] already exists", e.getMessage());
        }

        verify(repository).findById(anyString());
        verify(repository).findByEmail(request.email());
        verify(mapper, times(0)).update(any(), any());
        verify(encoder, times(0)).encode(request.password());
        verify(repository, times(0)).save(any(User.class));
    }



    @Test
    void whenCallUpdateWithValidParamsThenGetSuccess() {
        final var id = "1";
        final var request = generatedMock(UpdateUserRequest.class);
        final var entity = generatedMock(User.class).withId(id);

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));
        when(mapper.update(any(), any())).thenReturn(entity);
        when(repository.save(any(User.class))).thenReturn(entity);

        service.update(id, request);

        verify(repository).findById(anyString());
        verify(repository).findByEmail(request.email());
        verify(mapper).update(request, entity);
        verify(encoder).encode(request.password());
        verify(repository).save(any(User.class));
        verify(mapper).fromEntity(any(User.class));
    }
}
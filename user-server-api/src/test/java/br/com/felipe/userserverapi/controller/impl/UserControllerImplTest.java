package br.com.felipe.userserverapi.controller.impl;

import br.com.felipe.userserverapi.entity.User;
import br.com.felipe.userserverapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.request.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.felipe.userserverapi.creator.CreatorUtils.generatedMock;


import static org.springframework.data.mongodb.util.BsonUtils.toJson;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    public static final String BASE_URI = "/api/users";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testFindByIdWithSuccess() throws Exception {
        final var entity = generatedMock(User.class);
        final var userId = userRepository.save(entity).getId();

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        userRepository.deleteById(userId);
    }

    @Test
    void testFindByIdWithNotFound() throws Exception {

        mockMvc.perform(get("/api/users/{id}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object não encontrado id: 1, Type UserResponse"))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value("/api/users/1"))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

  @Test
    void testFindAllWithSuccess() throws Exception {
        final var entity = generatedMock(User.class);
        final var entity2 = generatedMock(User.class);

        userRepository.saveAll(List.of(entity, entity2));
        mockMvc.perform(get((BASE_URI))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty())
                .andExpect(jsonPath("$[0].profiles").isArray());


        userRepository.deleteAll(List.of(entity, entity2));


  }


  @Test
    void testSaveWithSuccess() throws Exception {
     final var request = generatedMock(CreateUserRequest.class).withEmail("email@email.com");

     mockMvc.perform(post((BASE_URI))
                     .
             contentType(APPLICATION_JSON_VALUE)
                     .content(objectMapper.writeValueAsString(request))
             ).andExpect(status().isCreated());

  userRepository.deleteByEmail("email@email.com");

    }


    @Test
    void testSaveWithConflict() throws Exception {
        final var validEmail = "felipe@gmail.com";
        final var entity = generatedMock(User.class).withEmail(validEmail);
        userRepository.save(entity);
        final var request = generatedMock(CreateUserRequest.class).withEmail(validEmail);

        mockMvc.perform(post(BASE_URI).
                contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isConflict())
                        .andExpect(jsonPath("$.message").value("Email [ " + validEmail + " ] already exists"))
                        .andExpect(jsonPath("$.error").value("Conflict"))
                        .andExpect(jsonPath("$.path").value("/api/users"))
                        .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                        .andExpect(jsonPath("$.timestamp").isNotEmpty());


        userRepository.deleteByEmail(validEmail);

    }

    @Test
    void testSaveUserWithNameEmptyThenThrowBadRequest() throws Exception{
        final var validEmail = "felipe@gmail.com";
        final var request = generatedMock(CreateUserRequest.class).withName("").withEmail(validEmail);

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Exception in validation attributes"))
                .andExpect(jsonPath("$.error").value("Validation Exception"))
                .andExpect(jsonPath("$.path").value(BASE_URI))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name' && @.message=='Name must contain between 3 and 50 characters')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name' && @.message=='Name cannot be empty')]").exists());
    }



    private String toJson(final Object object) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (final Exception e) {
            throw new Exception("Error to convert object to json", e);
        }

    }
}
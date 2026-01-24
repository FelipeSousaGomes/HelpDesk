package br.com.felipe.oderserviceapi.clients;

import io.swagger.v3.oas.annotations.Parameter;
import models.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service-api",
        path = "/api/users"
)
public interface UserServiceFeingClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @PathVariable(name = "id") final String id);


}

package br.com.felipe.authserviceapi.Authcontrollers.impl;

import br.com.felipe.authserviceapi.Authcontrollers.AuthController;
import models.request.AuthenticateRequest;
import models.request.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest request) {
        return ResponseEntity.ok().body(AuthenticateResponse.builder()
                        .type("Bearer")
                        .token("mocked-jwt-token")
                .build());

    }
}

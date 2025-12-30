package br.com.felipe.authserviceapi.security;

import br.com.felipe.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.felipe.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.request.AuthenticateRequest;
import models.request.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user with email: {}", request.email());
            final var authresult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            return buildAuthenticateResponse((UserDetailsDTO) authresult.getPrincipal());

        } catch (BadCredentialsException ex) {
            log.error("Error on authenticate user {}", request.email());
            throw new BadCredentialsException("Email or password invalid");
        }
    }

    protected AuthenticateResponse buildAuthenticateResponse(final UserDetailsDTO userDetails) {
        log.info("Successfully authenticated user: {}", userDetails.getUsername());
        final var token = jwtUtils.generateToken(userDetails);
        return AuthenticateResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}

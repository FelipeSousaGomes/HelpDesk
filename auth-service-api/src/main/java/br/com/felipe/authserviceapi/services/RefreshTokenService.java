package br.com.felipe.authserviceapi.services;

import br.com.felipe.authserviceapi.models.RefreshToken;
import br.com.felipe.authserviceapi.repositories.RefreshTokenRepository;
import br.com.felipe.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.felipe.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpired;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long refreshTokenExpirationSec;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final JWTUtils jwtUtils;

    public RefreshToken save(final String username){
        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID().toString())
                        .createdAt(now())
                        .expiresAt(now().plusSeconds(refreshTokenExpirationSec))
                        .username(username)
                        .build()
        );
    }
    public RefreshTokenResponse refreshToken(final String refreshTokenId) {
        final var refreshToken = refreshTokenRepository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(now())) {
            throw new RefreshTokenExpired("Refresh token expired");
        }
        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
        );
    }



}

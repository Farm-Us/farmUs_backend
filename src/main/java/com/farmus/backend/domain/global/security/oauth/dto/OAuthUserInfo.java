package com.farmus.backend.domain.global.security.oauth.dto;

public record OAuthUserInfo(
        String providerId,
        String email,
        String name
) {
}

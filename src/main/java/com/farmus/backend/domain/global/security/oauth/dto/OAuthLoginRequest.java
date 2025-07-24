package com.farmus.backend.domain.global.security.oauth.dto;

import com.farmus.backend.domain.user.entity.Provider;

public record OAuthLoginRequest(
        Provider provider,
        String accessToken
) {
}

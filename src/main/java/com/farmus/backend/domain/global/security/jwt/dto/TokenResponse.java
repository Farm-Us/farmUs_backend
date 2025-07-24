package com.farmus.backend.domain.global.security.jwt.dto;

import com.farmus.backend.domain.user.dto.UserDto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        UserDto userDto
) {
}

package com.farmus.backend.domain.user.dto;

import com.farmus.backend.domain.user.entity.User;

public record UserDto(
        Long userId,
        String email,
        String name,
        String profileImage,
        String role
) {
    public static UserDto of(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getProfileImage(),
                user.getRole().name()
        );
    }
}

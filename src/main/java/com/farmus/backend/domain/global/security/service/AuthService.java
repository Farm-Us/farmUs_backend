package com.farmus.backend.domain.global.security.service;

import com.farmus.backend.domain.global.security.jwt.JwtTokenProvider;
import com.farmus.backend.domain.global.security.jwt.dto.TokenResponse;
import com.farmus.backend.domain.global.security.jwt.repository.RefreshTokenRepository;
import com.farmus.backend.domain.global.security.oauth.OAuthProviderClient;
import com.farmus.backend.domain.global.security.oauth.dto.OAuthLoginRequest;
import com.farmus.backend.domain.global.security.oauth.dto.OAuthUserInfo;
import com.farmus.backend.domain.user.entity.Role;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuthProviderClient oAuthProviderClient;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse oauthLoginOrRegister(OAuthLoginRequest loginRequest) {
        OAuthUserInfo userInfo = oAuthProviderClient.getUserInfo(loginRequest.provider(), loginRequest.accessToken());

        User user = userRepository.findByProviderAndProviderId(loginRequest.provider(), userInfo.providerId())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(userInfo.email())
                            .name(userInfo.name())
                            .provider(loginRequest.provider())
                            .providerId(userInfo.providerId())
                            .role(Role.USER)
                            .build();
                    return userRepository.save(newUser);
                });
        return jwtTokenProvider.generateTokens(user);
    }
}

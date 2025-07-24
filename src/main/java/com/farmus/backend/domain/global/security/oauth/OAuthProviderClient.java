package com.farmus.backend.domain.global.security.oauth;

import com.farmus.backend.domain.global.security.oauth.dto.OAuthUserInfo;
import com.farmus.backend.domain.user.entity.Provider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OAuthProviderClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public OAuthUserInfo getUserInfo(Provider provider, String accessToken) {
        return switch (provider){
            case GOOGLE -> getGoogleUserInfo(accessToken);
            case KAKAO -> getKakaoUserInfo(accessToken);
            case NAVER -> getNaverUserInfo(accessToken);
        };

    }

    private OAuthUserInfo getGoogleUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map body = response.getBody();
        return new OAuthUserInfo(
                (String) body.get("id"),
                (String) body.get("email"),
                (String) body.get("name")
        );

    }

    private OAuthUserInfo getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> kakaoAccount = (Map<String, Object>) response.getBody().get("kakao_account");

        return new OAuthUserInfo(
                ((Number) response.getBody().get("id")).toString(),
                (String) kakaoAccount.get("email"),
                (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname")
        );
    }

    private OAuthUserInfo getNaverUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map body = (Map<String, Object>) response.getBody().get("response");

        return new OAuthUserInfo(
                (String) body.get("id"),
                (String) body.get("email"),
                (String) body.get("name")
        );
    }


}

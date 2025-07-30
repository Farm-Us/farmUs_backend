package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.user.entity.Provider;
import com.farmus.backend.domain.user.entity.Role;
import com.farmus.backend.domain.user.entity.User;
import lombok.Getter;

@Getter
public enum UserFixture {

    회원("test@naver.com", "김회원", "010-1234-5678", null, Role.PRODUCER, Provider.NAVER, "naver");

    private String email;
    private String name;
    private String phone;
    private String profileImage;
    private Role role;
    private Provider provider;
    private String providerId;

    UserFixture(String email, String name, String phone, String profileImage, Role role, Provider provider, String providerId) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static User 김회원() {return new User(회원.email, 회원.name, 회원.phone, 회원.profileImage, 회원.role, 회원.provider, 회원.providerId);}
}

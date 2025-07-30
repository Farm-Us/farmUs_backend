package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import lombok.Getter;

@Getter
public enum ProducerFixture {

    생산자("빽뺵복숭아", "경상북도 영천시", "과육이 빽빽하게 들어간 백도 복숭아가 자랑입니다.", "15년", "경북", null, "유기농", "농업인가증");

    private String farmName;
    private String region;
    private String introduction;
    private String career;
    private String address;
    private String profileImage;
    private String tags;
    private String certifications;

    ProducerFixture(String farmName, String region, String introduction, String career, String address, String profileImage, String tags, String certifications) {
        this.farmName = farmName;
        this.region = region;
        this.introduction = introduction;
        this.career = career;
        this.address = address;
        this.profileImage = profileImage;
        this.tags = tags;
        this.certifications = certifications;
    }

    public static ProducerProfile 생산자(User user) {
        return new ProducerProfile(user, 생산자.farmName, 생산자.region, 생산자.introduction, 생산자.career, 생산자.address, 생산자.profileImage, 생산자.tags, 생산자.certifications);
    }


}

package com.farmus.backend.domain.user.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.user.dto.ProducerProfileDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProducerProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String farmName;

    //지역
    private String region;

    private String introduction;

    private String career;

    //주소
    private String address;

    @Column(length = 255)
    private String profileImage;

    private String tags;

    private String certifications;

    public ProducerProfile(Long id, User user, String farmName, String region, String introduction, String career, String address, String profileImage, String tags, String certifications) {
        this.id = id;
        this.user = user;
        this.farmName = farmName;
        this.region = region;
        this.introduction = introduction;
        this.career = career;
        this.address = address;
        this.profileImage = profileImage;
        this.tags = tags;
        this.certifications = certifications;
    }

    public ProducerProfile(User user, String farmName, String region, String introduction, String career, String address, String profileImage, String tags, String certifications) {
        this(null, user, farmName, region, introduction, career, address, profileImage, tags, certifications);
    }
}

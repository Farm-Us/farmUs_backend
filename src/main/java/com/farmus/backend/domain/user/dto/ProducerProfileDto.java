package com.farmus.backend.domain.user.dto;

import com.farmus.backend.domain.user.entity.ProducerProfile;

public record ProducerProfileDto(
        Long producerId,
        Long userId,
        String farmName,
        String region,
        String introduction,
        String career,
        String address,
        String profileImage,
        String tags,
        String certifications
) {
    public static ProducerProfileDto from(ProducerProfile producerProfile) {
        return new ProducerProfileDto(
                producerProfile.getId(),
                producerProfile.getUser().getId(),
                producerProfile.getFarmName(),
                producerProfile.getRegion(),
                producerProfile.getIntroduction(),
                producerProfile.getCareer(),
                producerProfile.getAddress(),
                producerProfile.getProfileImage(),
                producerProfile.getTags(),
                producerProfile.getCertifications()
        );
    }
}


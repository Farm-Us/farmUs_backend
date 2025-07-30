package com.farmus.backend.domain.user.repository;

import com.farmus.backend.domain.user.entity.ProducerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerProfileRepository extends JpaRepository<ProducerProfile, Long> {
    default ProducerProfile findOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

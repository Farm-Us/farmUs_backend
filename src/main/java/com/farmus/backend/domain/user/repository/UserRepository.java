package com.farmus.backend.domain.user.repository;

import com.farmus.backend.domain.user.entity.Provider;
import com.farmus.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    Optional<User> findByEmail(String email);

    Optional<User> findByProviderAndProviderId(Provider provider, String providerId);
}

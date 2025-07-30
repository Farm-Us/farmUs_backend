package com.farmus.backend.domain.user.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String phone;

    @Column(length = 1024)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @OneToOne(mappedBy = "user")
    private ProducerProfile producerProfile;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();


    public User(Long id, String email, String name, String phone, String profileImage, Role role, Provider provider, String providerId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }


    public User(String email, String name, String phone, String profileImage, Role role, Provider provider, String providerId) {
        this(null, email, name, phone, profileImage, role, provider, providerId);
    }

}

package com.farmus.backend.domain.order.repository;

import com.farmus.backend.domain.order.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByOrderId(Long orderId);
}

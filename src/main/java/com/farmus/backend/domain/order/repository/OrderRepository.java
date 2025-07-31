package com.farmus.backend.domain.order.repository;

import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.exception.ItemErrorCode;
import com.farmus.backend.domain.item.exception.ItemException;
import com.farmus.backend.domain.order.entity.Order;
import com.farmus.backend.domain.order.exception.OrderErrorCode;
import com.farmus.backend.domain.order.exception.OrderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
    }

    Page<Order> findAllByUserId(Long userId, Pageable pageable);



}

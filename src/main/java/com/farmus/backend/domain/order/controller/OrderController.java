package com.farmus.backend.domain.order.controller;

import com.farmus.backend.domain.global.success.SuccessResponse;
import com.farmus.backend.domain.order.dto.OrderCreateRequest;
import com.farmus.backend.domain.order.dto.OrderDto;
import com.farmus.backend.domain.order.exception.OrderSuccessCode;
import com.farmus.backend.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse<OrderDto>> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderCreateRequest request
    ) {
        OrderDto order = orderService.createOrder(userId, request);
        return ResponseEntity.status(OrderSuccessCode.ORDER_CREATED.getStatus())
                .body(SuccessResponse.of(OrderSuccessCode.ORDER_CREATED, order));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<OrderDto>> getAllOrders(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.getOrders(userId, pageable));
    }

    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(userId, orderId));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId
    ){
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}

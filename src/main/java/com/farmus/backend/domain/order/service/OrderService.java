package com.farmus.backend.domain.order.service;

import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.repository.ItemRepository;
import com.farmus.backend.domain.order.dto.OrderCreateRequest;
import com.farmus.backend.domain.order.dto.OrderDto;
import com.farmus.backend.domain.order.dto.OrderItemRequest;
import com.farmus.backend.domain.order.entity.*;
import com.farmus.backend.domain.order.repository.DeliveryRepository;
import com.farmus.backend.domain.order.repository.OrderItemRepository;
import com.farmus.backend.domain.order.repository.OrderRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import com.farmus.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ProducerProfileRepository producerProfileRepository;

    public OrderDto createOrder(Long userId, OrderCreateRequest request) {
        User user = userRepository.findOrThrow(userId);

        Address address = new Address(
                request.address().zipcode(),
                request.address().street(),
                request.address().detail()
        );
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest oir : request.orderItems()) {
            Item item = itemRepository.findOrThrow(oir.itemId());
            ProducerProfile producer = producerProfileRepository.findOrThrow(oir.producerProfileId());
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getItemPrice(), oir.count());
            orderItems.add(orderItem);
        }

        Order order = Order.createOrder(user, delivery, orderItems, request.requestedDeliveryNote());
        orderRepository.save(order);

        return OrderDto.of(order);
    }

    @Transactional(readOnly = true)
    public OrderDto getOrder(Long userId, Long orderId) {
        User user = userRepository.findOrThrow(userId);
        Order order = orderRepository.findOrThrow(orderId);
        return OrderDto.of(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> getOrders(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).map(OrderDto::of);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOrThrow(orderId);
        order.cancel();
    }
}

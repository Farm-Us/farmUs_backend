package com.farmus.backend.domain.order.service;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.repository.CategoryRepository;
import com.farmus.backend.domain.item.repository.ItemRepository;
import com.farmus.backend.domain.order.entity.*;
import com.farmus.backend.domain.order.repository.DeliveryRepository;
import com.farmus.backend.domain.order.repository.OrderItemRepository;
import com.farmus.backend.domain.order.repository.OrderRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import com.farmus.backend.domain.user.repository.UserRepository;
import com.farmus.backend.support.annotation.ServiceTest;
import com.farmus.backend.support.fixture.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ServiceTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    ProducerProfileRepository producerProfileRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 주문을_할_수_있다() {
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);
        Category parentCategory = CategoryFixture.과일();
        categoryRepository.save(parentCategory);
        Category childCategory = CategoryFixture.복숭아(parentCategory);
        categoryRepository.save(childCategory);
        Item item1 = ItemFixture.백도(producer, childCategory);
        itemRepository.save(item1);
        Item item2 = ItemFixture.황도(producer, childCategory);
        itemRepository.save(item2);
        OrderItem orderItem1 = OrderItemFixture.주문상품1(item1);
        OrderItem orderItem2 = OrderItemFixture.주문상품2(item2);
        Delivery delivery = DeliveryFixture.배송지();
        Order order = OrderFixture.주문(user, List.of(orderItem1,orderItem2), delivery);
        orderRepository.save(order);

        assertThat(orderRepository.findAll()).hasSize(1);
        assertThat(order.getTotalPrice()).isEqualTo(133500);
    }
}

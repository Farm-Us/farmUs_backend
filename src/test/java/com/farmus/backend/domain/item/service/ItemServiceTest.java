package com.farmus.backend.domain.item.service;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.repository.CategoryRepository;
import com.farmus.backend.domain.item.repository.ItemRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import com.farmus.backend.domain.user.repository.UserRepository;
import com.farmus.backend.support.annotation.ServiceTest;
import com.farmus.backend.support.fixture.CategoryFixture;
import com.farmus.backend.support.fixture.ItemFixture;
import com.farmus.backend.support.fixture.ProducerFixture;
import com.farmus.backend.support.fixture.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

@ServiceTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProducerProfileRepository producerProfileRepository;

    @Test
    void 상품을_저장할_수_있다(){
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);
        Category parentCategory = CategoryFixture.과일();
        categoryRepository.save(parentCategory);
        Category childCategory = CategoryFixture.복숭아(parentCategory);
        categoryRepository.save(childCategory);
        Item item = ItemFixture.백도(producer, childCategory);
        itemRepository.save(item);

        assertThat(itemRepository.findAll()).hasSize(1);
    }

    @Test
    void 상품을_삭제할_수_있다(){
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);
        Category parentCategory = CategoryFixture.과일();
        categoryRepository.save(parentCategory);
        Category childCategory = CategoryFixture.복숭아(parentCategory);
        categoryRepository.save(childCategory);
        Item item = ItemFixture.백도(producer, childCategory);
        itemRepository.save(item);

        itemService.deleteItem(item.getId());
        assertThat(itemRepository.findAll()).hasSize(0);
    }
}

package com.fastcampus.shoploadbook.repository;

import com.fastcampus.shoploadbook.constant.ItemSellStatus;
import com.fastcampus.shoploadbook.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void clean() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 저장 테스트")
    void createItemTest() {
        // given
        Item item = Item.builder()
                .name("테스트 상품")
                .price(10000)
                .detail("테스트 상품 상세 설명")
                .sellStatus(ItemSellStatus.SELL)
                .quantity(100)
                .build();

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Assertions.assertEquals(10000, savedItem.getPrice());
        Assertions.assertEquals(ItemSellStatus.SELL, savedItem.getSellStatus());
    }
}
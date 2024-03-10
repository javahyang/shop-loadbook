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

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("상품명 조회 테스트")
    void findByNameTest() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByName("테스트 상품1");
        Item firstItem = itemList.stream().findFirst().orElse(
                Item.builder()
                    .name("임시상품")
                    .price(10000)
                    .detail("임시상품 상세 설명")
                    .sellStatus(ItemSellStatus.SELL)
                    .quantity(100)
                    .build()
        );

        // then
        Assertions.assertEquals("테스트 상품1", firstItem.getName());
    }

    private void createItemList() {
        List<Item> itemList = new ArrayList<>();
        for (int i = 1 ; i <= 10 ; i++) {
            Item item = Item.builder()
                    .name("테스트 상품"+i)
                    .price(10000+i)
                    .detail("테스트 상품 상세 설명"+i)
                    .sellStatus(ItemSellStatus.SELL)
                    .quantity(100)
                    .build();
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);
    }

    @Test
    @DisplayName("상품명 or 상품상세설명 테스트")
    void test() {
        // given
        createItemList();

        // when
        String name = "테스트 상품1";
        String detail = "테스트 상품 상세 설명5";
        List<Item> itemList = itemRepository.findByNameOrDetail(name, detail);

        // then
        Assertions.assertFalse(itemList.isEmpty());
        Assertions.assertEquals(2, itemList.size());
    }
}
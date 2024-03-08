package com.fastcampus.shoploadbook.entity;

import com.fastcampus.shoploadbook.constant.ItemSellStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    @DisplayName("Item 빌드 테스트")
    void buildItem() {
        // given
        Item item = Item.builder()
                .name("테스트 아이템")
                .detail("테스트 디테일")
                .sellStatus(ItemSellStatus.SELL)
                .price(1000)
                .quantity(10)
                .build();

        // then
        Assertions.assertEquals(1000, item.getPrice());
        Assertions.assertEquals(ItemSellStatus.SELL, item.getSellStatus());
    }
}
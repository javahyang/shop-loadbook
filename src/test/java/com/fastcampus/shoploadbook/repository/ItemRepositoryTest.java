package com.fastcampus.shoploadbook.repository;

import com.fastcampus.shoploadbook.constant.ItemSellStatus;
import com.fastcampus.shoploadbook.entity.Item;
import com.fastcampus.shoploadbook.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

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
    void findByNameOrDetailTest() {
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

    @Test
    @DisplayName("가격 LessThen 테스트")
    void findByPriceLessThenTest() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);

        // then
        Assertions.assertFalse(itemList.isEmpty());
        Assertions.assertEquals(4, itemList.size());
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    void findByPriceLessThanOrderByPriceDescTest() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        // then
        Assertions.assertFalse(itemList.isEmpty());
        Assertions.assertEquals("테스트 상품3",itemList.get(1).getName());
    }

    @Test
    @DisplayName("@Query 이용한 상품 조회 리스트")
    void findByDetailTest() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByDetail("테스트 상품 상세 설명");

        // then
        Assertions.assertEquals(10, itemList.size());
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    void findByDetailByNative() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByDetailByNative("테스트 상품 상세 설명");

        // then
        Assertions.assertEquals(10, itemList.size());
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    void queryDslTest() {
        // given
        createItemList();

        // when
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.sellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.detail.like("%"+"테스트 상품 상세 설명"+"%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();

        // then
        Assertions.assertEquals(10, itemList.size());
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    void queryDslTest2() {
        // given
        createItemList2();

        // when
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String detail = "테스트 상품 상세 설명";
        int price = 10003;
        String sellStat = "SELL";

        booleanBuilder.and(item.detail.like("%"+detail+"%"));
        booleanBuilder.and(item.price.gt(price));

        if (StringUtils.equals(sellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.sellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);

        // then
        Assertions.assertEquals(2, itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        Assertions.assertEquals("테스트 상품5", resultItemList.get(1).getName());
    }

    private void createItemList2() {
        List<Item> itemList = new ArrayList<>();
        for (int i = 1 ; i <= 5 ; i++) {
            Item item = Item.builder()
                    .name("테스트 상품"+i)
                    .price(10000+i)
                    .detail("테스트 상품 상세 설명"+i)
                    .sellStatus(ItemSellStatus.SELL)
                    .quantity(100)
                    .build();
            itemList.add(item);
        }
        for (int i = 6 ; i <= 10 ; i++) {
            Item item = Item.builder()
                    .name("테스트 상품"+i)
                    .price(10000+i)
                    .detail("테스트 상품 상세 설명"+i)
                    .sellStatus(ItemSellStatus.SOLD_OUT)
                    .quantity(100)
                    .build();
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);
    }
}
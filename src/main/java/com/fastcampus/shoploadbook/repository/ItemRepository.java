package com.fastcampus.shoploadbook.repository;

import com.fastcampus.shoploadbook.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByName(String name);
    List<Item> findByNameOrDetail(String name, String detail);
    List<Item> findByPriceLessThan(int price);
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);
}

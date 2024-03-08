package com.fastcampus.shoploadbook.entity;

import com.fastcampus.shoploadbook.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "item")
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus sellStatus;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;
}

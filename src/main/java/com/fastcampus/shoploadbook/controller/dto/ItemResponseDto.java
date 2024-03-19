package com.fastcampus.shoploadbook.controller.dto;

import java.time.LocalDateTime;

public record ItemResponseDto(
        Long id,
        String name,
        String detail,
        String sellStatus,
        int price,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated
) {
}

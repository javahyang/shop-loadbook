package com.fastcampus.shoploadbook.controller.dto;

public record MemberFormRequestDto(
        String name,
        String email,
        String password,
        String address
) {
}

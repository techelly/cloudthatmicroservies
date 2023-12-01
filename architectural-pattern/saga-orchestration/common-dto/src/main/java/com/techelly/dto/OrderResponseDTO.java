package com.techelly.dto;

import lombok.Data;

import java.util.UUID;

import com.techelly.enums.OrderStatus;

@Data
public class OrderResponseDTO {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Double amount;
    private OrderStatus status;

}

package com.techelly.dto;

import lombok.Data;

import java.util.UUID;

import com.techelly.enums.OrderStatus;

@Data
public class OrchestratorResponseDTO {

    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Double amount;
    private OrderStatus status;

}

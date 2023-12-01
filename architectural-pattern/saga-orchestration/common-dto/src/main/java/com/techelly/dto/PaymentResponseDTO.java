package com.techelly.dto;

import lombok.Data;

import java.util.UUID;

import com.techelly.enums.PaymentStatus;

@Data
public class PaymentResponseDTO {
    private Integer userId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}

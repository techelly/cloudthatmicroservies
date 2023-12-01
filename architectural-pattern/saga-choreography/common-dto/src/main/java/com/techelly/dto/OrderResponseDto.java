package com.techelly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.techelly.events.order.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderResponseDto {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private OrderStatus status;

}

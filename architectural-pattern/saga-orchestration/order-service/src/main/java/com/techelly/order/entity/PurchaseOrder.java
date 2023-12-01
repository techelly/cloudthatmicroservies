package com.techelly.order.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import com.techelly.enums.OrderStatus;

import java.util.UUID;

@Data
@ToString
public class PurchaseOrder {

    @Id
    private UUID id;
    private Integer userId;
    private Integer productId;
    private Double price;
    private OrderStatus status;

}
package com.techelly.order.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.techelly.order.entity.PurchaseOrder;

import java.util.UUID;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, UUID> {
}

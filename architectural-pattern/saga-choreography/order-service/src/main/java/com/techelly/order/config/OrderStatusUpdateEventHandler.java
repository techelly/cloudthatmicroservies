package com.techelly.order.config;

import com.techelly.order.entity.PurchaseOrder;
import com.techelly.order.repository.PurchaseOrderRepository;
import com.techelly.order.service.OrderStatusPublisher;
import com.techelly.events.inventory.InventoryStatus;
import com.techelly.events.order.OrderStatus;
import com.techelly.events.payment.PaymentStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class OrderStatusUpdateEventHandler {

    @Autowired
    private PurchaseOrderRepository repository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(final UUID id, Consumer<PurchaseOrder> consumer){
        this.repository
                .findById(id)
                .ifPresent(consumer.andThen(this::updateOrder));

    }

    private void updateOrder(PurchaseOrder purchaseOrder){
        if(Objects.isNull(purchaseOrder.getInventoryStatus()) || Objects.isNull(purchaseOrder.getPaymentStatus()))
            return;
        var isComplete = PaymentStatus.RESERVED.equals(purchaseOrder.getPaymentStatus()) && InventoryStatus.RESERVED.equals(purchaseOrder.getInventoryStatus());
        var orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isComplete){
            this.publisher.raiseOrderEvent(purchaseOrder, orderStatus);
        }
    }

}

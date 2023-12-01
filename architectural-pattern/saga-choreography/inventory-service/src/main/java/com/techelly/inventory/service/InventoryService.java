package com.techelly.inventory.service;

import com.techelly.dto.InventoryDto;
import com.techelly.events.inventory.InventoryEvent;
import com.techelly.events.inventory.InventoryStatus;
import com.techelly.events.order.OrderEvent;
import com.techelly.inventory.entity.OrderInventoryConsumption;
import com.techelly.inventory.repository.OrderInventoryConsumptionRepository;
import com.techelly.inventory.repository.OrderInventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private OrderInventoryRepository inventoryRepository;

    @Autowired
    private OrderInventoryConsumptionRepository consumptionRepository;

    @Transactional
    public InventoryEvent newOrderInventory(OrderEvent orderEvent){
        InventoryDto dto = InventoryDto.of(orderEvent.getPurchaseOrder().getOrderId(), orderEvent.getPurchaseOrder().getProductId());
        return inventoryRepository.findById(orderEvent.getPurchaseOrder().getProductId())
                .filter(i -> i.getAvailableInventory() > 0 )
                .map(i -> {
                    i.setAvailableInventory(i.getAvailableInventory() - 1);
                    consumptionRepository.save(OrderInventoryConsumption.of(orderEvent.getPurchaseOrder().getOrderId(), orderEvent.getPurchaseOrder().getProductId(), 1));
                    return new InventoryEvent(dto, InventoryStatus.RESERVED);
                })
                .orElse(new InventoryEvent(dto, InventoryStatus.REJECTED));
    }

    @Transactional
    public void cancelOrderInventory(OrderEvent orderEvent){
        consumptionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
                .ifPresent(ci -> {
                    inventoryRepository.findById(ci.getProductId())
                            .ifPresent(i ->
                                i.setAvailableInventory(i.getAvailableInventory() + ci.getQuantityConsumed())
                            );
                    consumptionRepository.delete(ci);
                });
    }

}

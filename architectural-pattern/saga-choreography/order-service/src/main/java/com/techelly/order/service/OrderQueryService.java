package com.techelly.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techelly.order.entity.PurchaseOrder;
import com.techelly.order.repository.PurchaseOrderRepository;

import java.util.List;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrder> getAll() {
        return this.purchaseOrderRepository.findAll();
    }

}

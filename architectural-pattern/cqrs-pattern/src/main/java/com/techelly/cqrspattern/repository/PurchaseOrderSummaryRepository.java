package com.techelly.cqrspattern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techelly.cqrspattern.entity.PurchaseOrderSummary;

import java.util.Optional;

@Repository
public interface PurchaseOrderSummaryRepository extends JpaRepository<PurchaseOrderSummary, String> {
    Optional<PurchaseOrderSummary> findByState(String state);
}

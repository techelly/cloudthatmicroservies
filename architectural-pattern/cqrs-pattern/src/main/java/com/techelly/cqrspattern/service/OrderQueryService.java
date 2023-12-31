package com.techelly.cqrspattern.service;

import java.util.List;

import com.techelly.cqrspattern.dto.PurchaseOrderSummaryDto;

public interface OrderQueryService {
	//Read
    List<PurchaseOrderSummaryDto> getSaleSummaryGroupByState();
    PurchaseOrderSummaryDto getSaleSummaryByState(String state);
    double getTotalSale();
}

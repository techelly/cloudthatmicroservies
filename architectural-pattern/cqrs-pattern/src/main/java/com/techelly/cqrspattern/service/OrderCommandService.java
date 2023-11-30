package com.techelly.cqrspattern.service;

public interface OrderCommandService {
    void createOrder(int userIndex, int productIndex);
    void cancelOrder(long orderId);
}

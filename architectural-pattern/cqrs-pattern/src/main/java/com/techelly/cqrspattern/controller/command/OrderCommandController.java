package com.techelly.cqrspattern.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import com.techelly.cqrspattern.dto.OrderCommandDto;
import com.techelly.cqrspattern.service.OrderCommandService;

@RestController
@RequestMapping("po")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class OrderCommandController {

    @Autowired
    private OrderCommandService orderCommandService;

    @PostMapping("/sale")
    public void placeOrder(@RequestBody OrderCommandDto dto){
        this.orderCommandService.createOrder(dto.getUserIndex(), dto.getProductIndex());
    }

    @PutMapping("/cancel-order/{orderId}")
    public void cancelOrder(@PathVariable long orderId){
        this.orderCommandService.cancelOrder(orderId);
    }
}

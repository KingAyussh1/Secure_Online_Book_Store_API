package com.example.bookstore.controller;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/checkout")
    public String placeOrder(@RequestBody Order order, @RequestHeader("Authorization") String token) {
        // Extract user from token and associate order
        String username = new JwtUtil().extractUsername(token.substring(7));
        User user = userService.findByUsername(username);
        order.setUser(user);
        order.setOrderDate(new Date());
        orderRepository.save(order);
        return "Order placed successfully!";
    }

    @GetMapping("/history")
    public List<Order> getOrderHistory(@RequestHeader("Authorization") String token) {
        String username = new JwtUtil().extractUsername(token.substring(7));
        User user = userService.findByUsername(username);
        return orderRepository.findByUser(user);
    }
}

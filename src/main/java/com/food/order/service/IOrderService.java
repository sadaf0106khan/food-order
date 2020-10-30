package com.food.order.service;

import com.food.order.storage.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    Order getOrderById(String id);
    List<Order> getOrderByUser();
    List<Order> getAllOrder();
    Order getCart();
    Order addToCart(String menuId);
    Order removeToCart(String menuId);
    Order deleteFromCart(String menuId);
    Order conformOrder();
    Order deliverOrder(String orderId);
}

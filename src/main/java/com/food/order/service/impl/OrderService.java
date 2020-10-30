package com.food.order.service.impl;

import com.food.order.repository.MenuRepository;
import com.food.order.repository.OrderRepository;
import com.food.order.service.IOrderService;
import com.food.order.storage.CartItem;
import com.food.order.storage.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    AddressService addressService;

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getOrderByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findByEmailAndOrdered(email,true);

    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findByOrdered(true);
    }

    @Override
    public Order getCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> optionalOrder = orderRepository.findByEmailAndOrdered(email, false);
        return optionalOrder.isEmpty()?null:optionalOrder.get(0);
    }

    @Override
    public Order addToCart(String menuId) {
        CartItem newItem = CartItem.builder()
                .id(UUID.randomUUID().toString())
                .menuItem(menuRepository.findById(menuId).get())
                .quantity(1)
                .build();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> optionalOrder = orderRepository.findByEmailAndOrdered(email, false);
        if(!optionalOrder.isEmpty()){
            Order order = optionalOrder.get(0);
            List<CartItem> list = order.getItems()
                    .stream().filter((item) -> item.getMenuItem().getId().equals(menuId)).collect(Collectors.toList());
            if(list.isEmpty()){
                order.getItems().add(newItem);
            }else {
                list.get(0).setQuantity(list.get(0).getQuantity()+1);
            }
            order.setItemTotal(order.getItemTotal() + newItem.getMenuItem().getPrice());
            return orderRepository.save(order);
        }else {
            Order order = Order.builder()
                    .id(UUID.randomUUID().toString())
                    .email(email)
                    .items(Arrays.asList(newItem))
                    .itemTotal(newItem.getMenuItem().getPrice())
                    .orderDate(new Date())
                    .delivered(false)
                    .ordered(false)
                    .build();
            return orderRepository.save(order);
        }

    }

    @Override
    public Order removeToCart(String menuId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> optionalOrder = orderRepository.findByEmailAndOrdered(email, false);
        Order order = optionalOrder.get(0);
        List<CartItem> list = order.getItems()
                .stream().filter((item) -> item.getMenuItem().getId().equals(menuId)).collect(Collectors.toList());
        if(list.get(0).getQuantity()==1){
            order.getItems().remove(list.get(0));
        }else {
            list.get(0).setQuantity(list.get(0).getQuantity()-1);
        }
        order.setItemTotal(order.getItemTotal() - list.get(0).getMenuItem().getPrice());
        return orderRepository.save(order);

    }

    @Override
    public Order deleteFromCart(String menuId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> optionalOrder = orderRepository.findByEmailAndOrdered(email, false);
        Order order = optionalOrder.get(0);
        List<CartItem> list = order.getItems()
                .stream().filter((item) -> item.getMenuItem().getId().equals(menuId)).collect(Collectors.toList());
        order.getItems().remove(list.get(0));
        order.setItemTotal(order.getItemTotal() - list.get(0).getMenuItem().getPrice()*list.get(0).getQuantity());
        return orderRepository.save(order);
    }

    @Override
    public Order conformOrder() {
        Order activeOrder = getCart();
        activeOrder.setAddress(addressService.getSelectedAddress());
        activeOrder.setBill(1.12*activeOrder.getItemTotal()+20);
        activeOrder.setOrdered(true);
        activeOrder.setOrderDate(new Date());
        orderRepository.save(activeOrder);
        return activeOrder;
    }

    @Override
    public Order deliverOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        Order deliveredOrder = null;
        if(order != null){
            order.setDeliverDate(new Date());
            order.setDelivered(true);
            deliveredOrder = orderRepository.save(order);
        }
        return deliveredOrder;
    }
}

package com.food.order.repository;

import com.food.order.storage.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByEmailAndOrdered(String email, boolean ordered);
    List<Order> findByOrdered(boolean ordered);

}

package com.food.order.repository;

import com.food.order.enums.MenuType;
import com.food.order.storage.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuRepository extends MongoRepository<MenuItem, String> {
    List<MenuItem> findByMenuType(MenuType type);
}

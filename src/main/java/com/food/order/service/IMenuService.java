package com.food.order.service;

import com.food.order.enums.MenuType;
import com.food.order.storage.MenuItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMenuService {
    public List<MenuItem> getMenuItemByType(MenuType menuType);
    public List<MenuItem> getMenuItem();
    public MenuItem addMenuItem(MenuItem menuItem);
    public MenuItem updateMenuItem(MenuItem menuItem, String id);
    public void deleteMenuItem(String id);
}

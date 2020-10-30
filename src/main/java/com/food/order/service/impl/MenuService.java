package com.food.order.service.impl;

import com.food.order.enums.MenuType;
import com.food.order.repository.MenuRepository;
import com.food.order.service.IMenuService;
import com.food.order.storage.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class MenuService implements IMenuService {
    @Autowired
    MenuRepository menuRepository;
    @Override
    public List<MenuItem> getMenuItemByType(MenuType menuType) {
        return menuRepository.findByMenuType(menuType);
    }

    @Override
    public List<MenuItem> getMenuItem() {
        return menuRepository.findAll();
    }

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem, String id) {
        MenuItem item = menuRepository.findById(id)
                .orElseThrow(() -> new ValidationException("not found"));
        return menuRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(String id) {
        menuRepository.deleteById(id);
    }
}

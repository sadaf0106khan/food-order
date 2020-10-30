package com.food.order.controller;

import com.food.order.enums.MenuType;
import com.food.order.service.impl.MenuService;
import com.food.order.service.impl.OrderService;
import com.food.order.storage.MenuItem;
import com.food.order.storage.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    MenuService menuService;

    @Autowired
    OrderService orderService;

    @GetMapping("/fontawesome")
    String fontawesome(){
        return "icon-fontawesome";
    }
    @GetMapping("/menu")
    ModelAndView menu(){
        List<MenuItem> breakfast = menuService.getMenuItemByType(MenuType.BREAKFAST);
        List<MenuItem> lunch = menuService.getMenuItemByType(MenuType.LUNCH);
        List<MenuItem> dinner = menuService.getMenuItemByType(MenuType.DINNER);
        List<MenuItem> dessert = menuService.getMenuItemByType(MenuType.DESSERT);
        List<MenuItem> drinks = menuService.getMenuItemByType(MenuType.DRINKS);
        List<MenuItem> wine_card = menuService.getMenuItemByType(MenuType.WINE_CARD);
        Map<String, Object> map = new HashMap<>();
        map.put("breakfasts",breakfast);
        map.put("lunches",lunch);
        map.put("dinners",dinner);
        map.put("desserts",dessert);
        map.put("drinks",drinks);
        map.put("wines",wine_card);
        map.put("item",new MenuItem());
        return new ModelAndView("table-menu",map);
    }

    @GetMapping("/menu/delete/{id}")
    String deleteItem(@PathVariable(value = "id") String id){
        menuService.deleteMenuItem(id);
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/add")
    String addItem(@ModelAttribute("item") @Valid MenuItem menuItem){
        menuItem.setId(UUID.randomUUID().toString());
        menuService.addMenuItem(menuItem);
        return "redirect:/admin/menu";
    }

    @GetMapping("/order")
    ModelAndView order(){
        List<Order> orders = orderService.getAllOrder();
        Map<String, Object> map = new HashMap<>();
        map.put("orders",orders);
        map.put("order", null);
        return new ModelAndView("table-order",map);
    }

    @GetMapping("/order/details")
    public ModelAndView orderDetail(@PathParam("orderId") String orderId){
        List<Order> orders = orderService.getAllOrder();
        Order order = orderService.getOrderById(orderId);
        orders = orders.stream().filter(o -> !(o.equals(order)) ).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("orders",orders);
        map.put("order",order);
        return new ModelAndView("table-order",map);
    }

    @GetMapping("/order/deliver")
    public String deliverOrder(@PathParam("orderId") String orderId){
        Order order = orderService.deliverOrder(orderId);
        return "redirect:/admin/order";
    }

}

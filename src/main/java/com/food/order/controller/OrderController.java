package com.food.order.controller;

import com.food.order.service.IAddressService;
import com.food.order.service.IOrderService;
import com.food.order.storage.Address;
import com.food.order.storage.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    @Autowired
    IAddressService addressService;

    @GetMapping("/get")
    public ModelAndView getOrders(){
        List<Order> orders = orderService.getOrderByUser();
        Map<String, Object> map = new HashMap<>();
        map.put("orders",orders);
        map.put("order", null);
        return new ModelAndView("order",map);

    }

    @GetMapping("/details")
    public ModelAndView orderDetail(@PathParam("orderId") String orderId){
        List<Order> orders = orderService.getOrderByUser();
        Order order = orderService.getOrderById(orderId);
        orders = orders.stream().filter(o -> !(o.equals(order)) ).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("orders",orders);
        map.put("order",order);
        return new ModelAndView("order",map);
    }
    @GetMapping("/cart")
    public String cart(Model model){
        Address address = addressService.getSelectedAddress();
        model.addAttribute("order",orderService.getCart());
        model.addAttribute("address",address);
        return "cart";
    }
    @GetMapping("/cart/add")
    public String addCart(@RequestParam(value = "menuId") String menuId, Model model){
        Order order = orderService.addToCart(menuId);
        model.addAttribute("order",order);
        return "redirect:/order/cart";
    }

    @GetMapping("/cart/remove")
    public String removeToCart(@RequestParam(value = "menuId") String menuId, Model model){
        Order order = orderService.removeToCart(menuId);
        model.addAttribute("order",order);
        return "redirect:/order/cart";
    }

    @GetMapping("/cart/delete")
    public String deleteFromCart(@RequestParam(value = "menuId") String menuId, Model model){
        Order order = orderService.deleteFromCart(menuId);
        model.addAttribute("order",order);
        return "redirect:/order/cart";
    }

    @GetMapping("/pgredirect")
    public ModelAndView payment(){
        Order activeOrder = orderService.conformOrder();
        List<Order> orders = orderService.getOrderByUser();
        Map<String, Object> map = new HashMap<>();
        map.put("orders",orders);
        map.put("order",activeOrder);
        return new ModelAndView("order",map);
    }



}

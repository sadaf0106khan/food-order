package com.food.order.controller;

import com.food.order.dto.GenericResponse;
import com.food.order.dto.UserDto;
import com.food.order.enums.MenuType;
import com.food.order.service.IMenuService;
import com.food.order.service.IUserService;
import com.food.order.storage.MenuItem;
import com.food.order.storage.User;
import com.food.order.storage.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
@Slf4j
public class UserController {
    @Autowired
    IUserService service;

    @Autowired
    IMenuService menuService;
    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationForm(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "reg";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, Model model) throws Exception {
        User registered = service.registerUser(userDto);
        if(registered==null){
            model.addAttribute("message", "Email is already registered!!");
            model.addAttribute("user", userDto);
        }else {
            model.addAttribute("message","An email has been sent to you!!");
            model.addAttribute("user",new UserDto());
        }
        return "reg";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request,Model model, @RequestParam("token") String token){
        VerificationToken verificationToken = service.getVerificationToken(token);
        Locale locale = request.getLocale();
        if(verificationToken == null){
            model.addAttribute("message", "invalid verification token");
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        if(verificationToken.getExpiryTime().getTime()-new Date().getTime()<=0){
            model.addAttribute("message","token expired");
            model.addAttribute("expired",true);
            model.addAttribute("token",token);
            log.info("expired");
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        service.saveRegisteredUser(user);
        return "redirect:/login";
    }

    @GetMapping("/resendToken")
    public GenericResponse resendToken(@RequestParam VerificationToken existingToken){
        VerificationToken newToken = service.generateVerificationToken(existingToken.getUser());
        service.sendEmail(newToken);
        return new GenericResponse("message sent");
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }


    @GetMapping("/menu")
    public ModelAndView menu(){
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
        return new ModelAndView("menu",map);
    }





}

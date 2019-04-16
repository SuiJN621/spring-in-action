package com.sjn.springinaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sjn.springinaction.entity.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sui
 * @date 2019.04.16 10:35
 */
@Slf4j
@Controller
@RequestMapping("/orders")
public class TacoOrderController {

    @GetMapping("/current")
    public String current(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(Order order) {
        log.info("Order submitted: " + order);
        return "redirect:/";
    }
}

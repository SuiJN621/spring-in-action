package com.sjn.springinaction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.sjn.springinaction.repository.jpa.JpaOrderRepository;
import com.sjn.springinaction.entity.Order;
import com.sjn.springinaction.repository.jdbc.OrderRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sui
 * @date 2019.04.16 10:35
 */
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class TacoOrderController {

    private OrderRepository orderRepo;
    private JpaOrderRepository jpaOrderRepository;
    @Autowired
    public TacoOrderController(OrderRepository orderRepo, JpaOrderRepository jpaOrderRepository) {
        this.orderRepo = orderRepo;
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @GetMapping("/current")
    public String current(Model model){
        //model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order,
                               //Validation错误对象, 也可以使用BindingResult
                               Errors errors,
                               //获取session状态
                               SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: " + order);
        //orderRepo.save(order);
        jpaOrderRepository.save(order);
        //清除session数据
        sessionStatus.setComplete();
        return "redirect:/";
    }
}

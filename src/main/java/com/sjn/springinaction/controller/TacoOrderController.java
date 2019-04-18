package com.sjn.springinaction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.sjn.springinaction.config.custom.OrderProps;
import com.sjn.springinaction.entity.User;
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
    private OrderProps orderProps;

    @Autowired
    public TacoOrderController(OrderRepository orderRepo, JpaOrderRepository jpaOrderRepository, OrderProps orderProps) {
        this.orderRepo = orderRepo;
        this.jpaOrderRepository = jpaOrderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String current(@AuthenticationPrincipal User user,
                          @ModelAttribute Order order){
        //model.addAttribute("order", new Order());
        //获取用户设置回显数据
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order,
                               //Validation错误对象, 也可以使用BindingResult
                               Errors errors,
                               //获取session状态
                               SessionStatus sessionStatus,
                               //注解等效于Authentication.getPrincipal()
                               //更通用的获取用户的方式
                               //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);
        log.info("Order submitted: " + order);
        //orderRepo.save(order);
        jpaOrderRepository.save(order);
        //清除session数据
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
                jpaOrderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}

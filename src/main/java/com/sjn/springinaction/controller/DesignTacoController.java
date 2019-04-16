package com.sjn.springinaction.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sjn.springinaction.entity.Ingredient;
import com.sjn.springinaction.entity.Taco;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sui
 * @date 2019.04.15 16:39
 */
@Slf4j  //自动生成private static Logger log = LoggerFactory.getLogger(DesignTacoController.class);
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model){      //model对象负责在controller和view之间传输数据, 数据复制到response.attribute中
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String createDesign(Taco taco){

        log.info("Design:{}", taco);

        //return new RedirectView("/orders/current");
        //使用redirect:前缀好处: 解耦, 灵活处理跳转/非跳转
        //跳转使用当前servlet context
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(i -> type.equals(i.getType())).collect(Collectors.toList());
    }
}

package tacos.controller.general_api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.entity.Ingredient;
import tacos.entity.Order;
import tacos.entity.Taco;
import tacos.repository.jdbc.IngredientRepository;
import tacos.repository.jdbc.TacoRepository;
import tacos.repository.jpa.JpaIngredientRepository;
import tacos.repository.jpa.JpaTacoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sui
 * @date 2019.04.15 16:39
 */
@Slf4j  //自动生成private static Logger log = LoggerFactory.getLogger(DesignTacoController.class);
@Controller
@RequestMapping("/design")
@SessionAttributes("order") //将order标记为session属性, 让其存活在session中, 联系多个请求
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    private JpaTacoRepository jpaTacoRepository;
    private JpaIngredientRepository jpaIngredientRepository;
    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository designRepo,
            JpaTacoRepository jpaTacoRepository,
            JpaIngredientRepository jpaIngredientRepository) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
        this.jpaTacoRepository = jpaTacoRepository;
        this.jpaIngredientRepository = jpaIngredientRepository;
    }

    /**
     * 在此Controller中所有Model对象中创建order, model.addAttribute("order", new Order())
     * - @ModelAttribute修饰的方法会在@RequestMapping修饰的方法之前执行
     * - 如果controller被@ControllerAdvice修饰, 此方法会对所有Controller有效
     */
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(
            //model对象负责在controller和view之间传输数据, 数据复制到response.attribute中
            Model model){

        addIngredientModelAttribute(model);

        //model.addAttribute("taco", new Taco());  使用@ModelAttribute方法代替
        return "design";
    }

    @PostMapping
    public String createDesign(@Valid Taco taco, Errors errors,
            //参数上标记@ModelAttribute, 如果model不存在此参数, 会先初始化一个, 如果有此参数, 会用请求参数给它赋值
            @ModelAttribute("order") Order order, Model model){
        if (errors.hasErrors()) {
            addIngredientModelAttribute(model);
            return "design";
        }
        log.info("Design:{}", taco);
        //Taco save = designRepo.save(taco);
        Taco save = jpaTacoRepository.save(taco);
        order.addDesign(save);

        //return new RedirectView("/orders/current");
        //使用redirect:前缀好处: 解耦, 灵活处理跳转/非跳转
        //跳转使用当前servlet context
        return "redirect:/orders/current";
    }

    private void addIngredientModelAttribute(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        //ingredientRepo.findAll().forEach(ingredients::add);
        jpaIngredientRepository.findAll().forEach(ingredients::add);

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(i -> type.equals(i.getType())).collect(Collectors.toList());
    }
}

package tacos.controller.general_api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.config.WebConfig;

/**
 * @author Sui
 * @date 2019.04.15 14:20
 */
@Controller
public class HomeController {

    /**
     * 可以使用简单方式实现
     * @see WebConfig
     * @return
     */
    @GetMapping("/")
    public String home(){
        return "home";
    }
}

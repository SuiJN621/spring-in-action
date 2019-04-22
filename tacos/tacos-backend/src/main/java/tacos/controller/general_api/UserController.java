package tacos.controller.general_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.formbean.RegistrationForm;
import tacos.repository.jpa.UserRepository;

/**
 * @author Sui
 * @date 2019.04.18 9:58
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(
            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/user/login";
    }
}

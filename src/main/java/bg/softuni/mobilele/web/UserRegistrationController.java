package bg.softuni.mobilele.web;

import bg.softuni.mobilele.model.dto.UserRegisterDTO;
import bg.softuni.mobilele.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userModel")
    public void initUserModel(Model model){// от контролера към юзъра
        model.addAttribute("userModel", new UserRegisterDTO());// добавя се за всички мапинги в контролера, преди да се изпълнят мапингите
    }

    @GetMapping("/register")
    public String register(){
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userModel,// от юзъра към контролера
                           BindingResult bindingResult,// свързан с валидирания аргумент преди него
                           RedirectAttributes redirectAttributes){//vryshta stinost vyv formata

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userModel",userModel);// презаписва  модел атрибута
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel",bindingResult);

            return "redirect:/users/register";
        }
        userService.registerAndLogin(userModel);
        return "redirect:/";
    }
}

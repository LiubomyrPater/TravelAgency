package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.controller.validator.UserValidator;
import com.portfolio.travelAgency.entity.User;
import com.portfolio.travelAgency.repository.RoleRepository;
import com.portfolio.travelAgency.service.dto.UserDTO;
import com.portfolio.travelAgency.service.event.RegisterUserEvent;
import com.portfolio.travelAgency.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class LoginController {

    private final RoleRepository roleRepository;

    private final UserService userService;
    private final UserValidator userValidator;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping
    public String basePage(Principal principal)
    {
        if(principal != null){
            if (userService.findByEmail(principal.getName())
                    .getRole()
                    .contains(roleRepository.findByName("ROLE_ADMIN"))
                    )
                return "redirect:management";
            else
                return "redirect:home";
        }
        return "commonPages/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal principal) {
        if(principal != null)
            return "redirect:/home";
        return "commonPages/login";
    }


    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "commonPages/registration";
    }

    @GetMapping("/confirmRegistration")
    public String confirmRegistration(@RequestParam("token") String token) {
        userService.confirmRegistration(token);
        return "redirect:/login";
    }


    @GetMapping("/default")
    public String getDefaultPage(Principal principal) {
        if (userService.findByEmail(principal.getName())
                .getRole()
                .contains(roleRepository.findByName("ROLE_ADMIN"))
                )
            return "redirect:admin";
        else
            return "redirect:home";
    }


    @PostMapping("/registration")
    public String registration( @ModelAttribute("userForm") UserDTO userDTO,
                                HttpServletRequest request,
                                BindingResult bindingResult)
    {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "commonPages/registration";
        }
        User user = userService.registerNewUser(userDTO);
        eventPublisher.publishEvent(new RegisterUserEvent(this, user, request.getContextPath()));
        return "commonPages/success";
    }
}

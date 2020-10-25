package ru.isu.ordermanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isu.ordermanager.domain.model.AutoUser;
import ru.isu.ordermanager.domain.repository.AutoUserRepository;

@Controller
public class HomeController {

    @Autowired
    private AutoUserRepository userRepository;

    @RequestMapping(value="/")
    public String home(){
        return "home";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String goLogin(){
        return "login";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute AutoUser user) {
        user.setRole("ROLE_USER");
        userRepository.save(user);

        //Program authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegister() {
        return "register";
    }

}

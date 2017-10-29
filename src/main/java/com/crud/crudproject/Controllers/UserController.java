package com.crud.crudproject.Controllers;

import com.crud.crudproject.Forms.Login;
import com.crud.crudproject.Models.User;
import com.crud.crudproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //display home page
    @RequestMapping("/")
    public String home(){
        return "index";
    }

    //display login form
    @GetMapping("/login")
    public String login(Login login){
        return "login";
    }

    //login authentication
    @PostMapping("/login")
    public String verifyLogin(@Valid Login login, BindingResult bindingResult, HttpSession session, Model model, RedirectAttributes redirectAttributes){
        User user = userService.authenticate(login);
        if(bindingResult.hasErrors()){
            return "login";
        }
        if (user == null) {
            redirectAttributes.addFlashAttribute("loginMessage","Wrong credentials.");
            return "redirect:/login";
        }
        session.setAttribute("loggedInUser", user);
        return "redirect:/";
    }

    //displays register form
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    //adds the new user to the database
    @PostMapping("/register")
    public String saveUser(@Valid User user, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "register";
        }
        try {
            User savedUser = userService.register(user);
            redirectAttributes.addFlashAttribute("regMessage","Registration was successful");
            return "redirect:/login";
        } catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("duplicate","Username already in use.");
            return "redirect:/register";
        }

    }

    //logout the user by deleting session data
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loggedInUser");
        return home();
    }

}

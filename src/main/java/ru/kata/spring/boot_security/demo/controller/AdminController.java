package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        List<ru.kata.spring.boot_security.demo.model.User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/add")
    public String showAddUser ( @RequestParam("name") String name,
                                @RequestParam("age") Integer age,
                                @RequestParam("job") String job,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setJob(job);
        user.setUsername(username);
        user.setPassword(password);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUsers (@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveUsers(@RequestParam("id") Long id,
                            @RequestParam("name") String name,
                            @RequestParam("age") Integer age,
                            @RequestParam("job") String job){
        User user = userService.findById(id);
        user.setName(name);
        user.setAge(age);
        user.setJob(job);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUsers(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

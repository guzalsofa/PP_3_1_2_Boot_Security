package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/add")
    public String showAddUser ( @RequestParam("name") String name,
                                @RequestParam("age") Integer age,
                                @RequestParam("job") String job,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam(value = "roles", required = false) List<String> roleNames) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setJob(job);
        user.setUsername(username);
        user.setPassword(password);
        if (roleNames != null && !roleNames.isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : roleNames) {
                roles.add(roleService.findRoleByName(roleName));
            }
            user.setRole(roles);
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUsers (@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        Set<Role> allRoles = roleService.findAllRoles();
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

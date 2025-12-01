package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
         return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        if (user.getRole() == null || user.getRole().isEmpty()){
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findRoleByName("ROLE_USER"));
            user.setRole(roles);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional
    @Override
    public User findByUsername(String username){ return userDao.findUserByUsername(username);}
}

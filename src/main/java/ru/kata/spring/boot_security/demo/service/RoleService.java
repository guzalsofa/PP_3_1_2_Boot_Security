package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findAllRoles();
    Role findRoleByName(String name);
    void addRole(Role role);
}

package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Override
    public Role findRoleByName(String name) {
        return roleDao.findByRole(name);
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}

package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Role> findAllRoles() {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Role findByRole(String role) {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r where r.role= :role", Role.class);
        return query.setParameter("role", role)
                .getResultStream()
                .findFirst()
                .orElse(null);
        }

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }
}

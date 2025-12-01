package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = false;
        boolean isUser = false;

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority(); // "ROLE_ADMIN" / "ROLE_USER"

            if ("ROLE_ADMIN".equals(role)) {
                isAdmin = true;
            }
            if ("ROLE_USER".equals(role)) {
                isUser = true;
            }
        }
        if (isAdmin) {
            redirectStrategy.sendRedirect(request, response, "/admin");
        } else if (isUser) {
            redirectStrategy.sendRedirect(request, response, "/user");
        } else {
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }
    }
package org.ruserrormak.restweb.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/user/info";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/admin/showUsers";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}

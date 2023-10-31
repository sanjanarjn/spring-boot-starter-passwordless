package com.springboot.passwordless.authentication;

import com.bitwarden.passwordless.PasswordlessClient;
import com.bitwarden.passwordless.error.PasswordlessApiException;
import com.bitwarden.passwordless.model.VerifiedUser;
import com.bitwarden.passwordless.model.VerifySignIn;
import com.springboot.passwordless.util.ServiceConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Slf4j
public class PasswordlessAuthenticationFilter extends OncePerRequestFilter {

    private PasswordlessClient passwordlessClient;

    public PasswordlessAuthenticationFilter(@Autowired PasswordlessClient passwordlessClient) {
        this.passwordlessClient = passwordlessClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        VerifiedUser verifiedUser = null;
        String token = request.getParameter(ServiceConstants.TOKEN_PARAM_KEY);
        if (token != null && !token.isBlank()) {
            try {
               verifiedUser = passwordlessClient.signIn(new VerifySignIn(token));
            } catch (PasswordlessApiException e) {
                //TODO Check the problem details and take appropriate action!
                log.error("Error signing in the user");
                throw new RuntimeException(e);
            }
        }

        if (verifiedUser != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(verifiedUser, null,
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}

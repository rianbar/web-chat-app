package com.rian.webchat.configuration;

import com.rian.webchat.errors.AuthenticationException;
import com.rian.webchat.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository repository;

    @Autowired
    SecurityFilter(TokenService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var token = this.recoverToken(request);

            if(token != null) {
                var nickname = tokenService.validateToken(token);
                Optional<UserDetails> user = repository.findByNickname(nickname);

                if (user.isPresent()) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null,
                            user.get().getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new AuthenticationException("server authentication error");
                }
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException error) {
            throw new AuthenticationException("server authentication error");
        }

    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}

package com.swp08.dpss.security.jwt;

// Intercepts requests to extract and validate JWT, then sets authentication in the context.

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER);
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith(PREFIX)) {
            jwt = authHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (JwtException e) {
                logger.warn("Invalid JWT token");
                logger.warn("Unable to extract JWT: " + e.getMessage());
                return;
            }
        } else {
            logger.debug("No Bearer token found in Authorization header");
        }

        // If user not yet authenticated in current request
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // User is still valid, set authentication
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails != null && jwt != null && jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}

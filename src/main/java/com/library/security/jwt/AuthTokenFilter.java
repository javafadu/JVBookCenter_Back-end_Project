package com.library.security.jwt;

import com.library.domain.User;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
public class AuthTokenFilter extends OncePerRequestFilter {

    // Request is faced this FILTER before going to resource
    // check token is valid or right token

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    // request is faced this filter before going resource

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                Long id = jwtUtils.getIdFromJwtToken(jwt);

                Optional<User> user = userRepository.findById(id);
                // set attribute name as id, request.id as currently logged in user id
                // getIdFromJwtToken in jwtUtils
                // (after passing token validation)
                request.setAttribute("id", user.get().getId());

                // our username is email
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getEmail());

                // create authentication object here
                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // set currently logged in user (authentication) in Security Context Holder
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            logger.error("User Authentication error");
        }

        filterChain.doFilter(request, response);

    }

    // parse of token method
    private String parseJwt(HttpServletRequest request) {
        // get Authorization in Header
        String headerAuth = request.getHeader("Authorization");
        // if this Authorization Header include a String text and it starts with "Bearer "
        // it means there is a token
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
            // ("Bearer "(Token starrts here (7. index) to end)
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match("/register", request.getServletPath())
                || antPathMatcher.match("/login", request.getServletPath());
    }
}

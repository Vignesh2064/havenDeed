package com.heavendeeds.heavendeeds.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import com.heavendeeds.heavendeeds.repository.HAVEENuserDetailsRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENUserRoleMapRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    HAVEENuserDetailsRepo HAVEENuserDetailsRepo;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HAVEENUserRoleMapRepo HAVEENUserRoleMapRepo;


    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    UserDetailsServiceImpl jwtUserDetailsService;
    JwtService jwtTokenUtils;
    List<String> whiteListUri = Arrays.asList("/swagger-ui/**","/haveenUserRegister/**","/haveenProperty/**","/propertyPhoto/**","/category/**","/search/**","/filter/**","/key/**");
    List<String> blackListUri = Arrays.asList("/health-check","/api/gateway/**");

    public JwtRequestFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenUtils = jwtService;
        this.jwtUserDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {
                String basicAuth = null;
                if("/api/gateway/login".equals(request.getRequestURI())) {
                    basicAuth = request.getHeader("Authorization").split("Basic ")[0];
                }
                String jwt = parseJwt(request);
                String urlPath = request.getServletPath();
                if (basicAuth != null) {
                    byte[] decodedBytes = Base64.getDecoder().decode(basicAuth);
                    String decodedString = new String(decodedBytes);
                    List<String> authDetails = Arrays.stream(decodedString.split(":")).toList();
                    logger.info("User {} initiated login.", authDetails.get(0));
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authDetails.get(0));
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDetails.get(0),
                            authDetails.get(1), userDetails.getAuthorities()));
                    Optional<HAVEENuserDetails> user = HAVEENuserDetailsRepo.findByEmail(authDetails.get(0));
                    if (user.isEmpty()) {
                        logger.info("User {} does not exists.", authDetails.get(0));
                        throw new AuthenticationException("User does not exists.");
                    }
//                    if (passwordEncoder.matches(authDetails.get(1), user.get().getForMuserAuthenticationDetails().getPassword())) {
//                        HAVEENuserDetailsDto userDetails1 = objectMapper.convertValue(user.get(), HAVEENuserDetailsDto.class);
//                        FORMuserRoleMap forMuserRoleMap = formUserRoleMapRepo.findByUserId(userDetails1.getUserId());
//                        jwt = jwtService.generateJwtToken(authentication);
//                        logger.info("Jwt token generated for user.");
////                        userDetails1.setToken(jwt);
////                        userDetails1.setRole(forMuserRoleMap.getForMuserRoles().getRole());
//                        Cookie cookie = new Cookie("newmeforum-token", jwt);
//                        cookie.setPath("/");
//                        cookie.setHttpOnly(true);
////                        cookie.setSecure(true);
//                        response.addCookie(cookie);
//                        logger.info("Cookie is added in response.");
//                    } else {
//                        response.setStatus(HttpStatus.CONFLICT.value());
//                        logger.error("Password is wrong for the user.{}", authDetails.get(1));
//                        throw new AuthenticationException("Password is wrong.");
//                    }
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else if (jwt != null && jwtTokenUtils.validateJwtToken(jwt)) {
                    String username = jwtTokenUtils.getUserNameFromJwtToken(jwt);
                    logger.info("User {} has accessed {}", username, urlPath);
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else{
                    throw new AuthenticationException("Authorization or jwt token in missing.");
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e.getMessage());
                try {
                    throw new AuthenticationException(e.getMessage());
                } catch (AuthenticationException ex) {
                    throw new RuntimeException(ex);
                }
            }
            filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<RequestMatcher> whiteListMatchers = whiteListUri.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        RequestMatcher whiteListMatcher = new OrRequestMatcher(whiteListMatchers);
        if (whiteListMatcher.matches(request)) {
            return true;
        }

        List<RequestMatcher> blackListMatchers = blackListUri.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        RequestMatcher blackListMatcher = new OrRequestMatcher(blackListMatchers);

        return !blackListMatcher.matches(request);
    }

//    private String parseJwt(HttpServletRequest request) {
//        Cookie jwtToken = Objects.nonNull(request.getCookies()) ? Arrays.stream(request.getCookies()).filter(p -> "biblical-token".equals(p.getName())).findFirst().orElse(null) : null;
//        if (!Objects.nonNull(jwtToken)) {
//            throw new RuntimeException("Request Cookie is missing");      }
//        return jwtToken.getValue();
//    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Basic ")) {
            return headerAuth.substring(6);
        }

        Cookie jwtToken = null;
        String tokenName = "newmeforum-token";
        if (Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (tokenName.equals(cookie.getName())) {
                    jwtToken = cookie;
                    break;
                }
            }
        }
        if (jwtToken == null) {
            logger.error("Authorization in header is not available and Request Cookie is missing.");
            return null;
        }
        return jwtToken.getValue();
    }

}
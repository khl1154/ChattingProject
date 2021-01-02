package com.clone.chat.config.security.jwt;


import com.clone.chat.config.security.WebSecurityConfigurerAdapter;
import com.clone.chat.model.Token;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    public SecretKey secretKey;
    public static final String JWT_TOKEN_PROCESSES_URL = WebSecurityConfigurerAdapter.SECURITY_PATH + "/user-sign-in";
    //    private final RedisTemplate<String, Object> redisTemplate;
    private final TokenService tokenService;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtConfig jwtConfig,
                                                      TokenService tokenService,
                                                      UserRepository userRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        setFilterProcessesUrl(JWT_TOKEN_PROCESSES_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            authenticationRequest.setPassword(authenticationRequest.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);

            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        String token = tokenService.makeToken(authResult.getName(), authResult.getAuthorities());
        String fullToken = jwtConfig.getTokenPrefix() + token;
        response.addHeader(HttpHeaders.AUTHORIZATION, fullToken);

        Token tokenObj = Token.builder().name(authResult.getName()).token(fullToken).build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(tokenObj));
//        if (null == userAuthToken) {
//            ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();
//            redisTemplate.opsForHash().put("api-user-tokens", authResult.getName(),
//                    UserAuthToken.builder().name(authResult.getName()).refreshToken(refreshtoken).token(fullToken).build()
//            );
//        }
//        List<Code> list = cache.get(SimpleKey.EMPTY, List.class);

//        UUID.randomUUID()
//        Cookie cookie = new Cookie(jwtConfig.getAuthorizationHeader(), token);
//        cookie.setPath("/");
//        response.addCookie(cookie);
    }
}
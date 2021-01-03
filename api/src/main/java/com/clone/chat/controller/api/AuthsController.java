package com.clone.chat.controller.api;

import com.clone.chat.domain.User;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.service.TokenService;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(AuthsController.URI_PREFIX)
@Slf4j
@Api(tags = "권한")
public class AuthsController {
    public static final String URI_PREFIX = ApiController.URI_PREFIX + "/auths";

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/details")
    @JsonView({JsonViewApi.class})
    public Optional<User> refresh(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestHeader(value= HttpHeaders.AUTHORIZATION) String authorization_header
            ) {
        return tokenService.getUserFromJwtHeader(authorization_header);
    }

}

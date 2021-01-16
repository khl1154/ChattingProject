package com.clone.chat.controller.api.friends;

import com.clone.chat.annotation.ModelAttributeMapping;
import com.clone.chat.controller.api.ApiController;
import com.clone.chat.domain.User;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.repository.UserRepository;
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
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(FriendsController.URI_PREFIX)
@Slf4j
@Api(tags = "친구")
public class FriendsController {
    public static final String URI_PREFIX = ApiController.URI_PREFIX + "/friends";

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;



    @GetMapping(value = {"", "/"})
    @JsonView({JsonViewApi.class})
    public List<User> friends(HttpServletRequest request, HttpServletResponse response, @ModelAttributeMapping UserToken userToken) {
        return userRepository.findById(userToken.getId()).get().getFriends();
    }


    @PostMapping(value = "/details")
    @JsonView({JsonViewApi.class})
    public User refresh(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestHeader(value= HttpHeaders.AUTHORIZATION) String authorization_header
            ) {
        return tokenService.getUserFromJwtHeader(authorization_header);
    }

}

package com.clone.chat.controller.api.friends;

import com.clone.chat.annotation.ModelAttributeMapping;
import com.clone.chat.controller.api.ApiController;
import com.clone.chat.domain.User;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.FriendService;
import com.clone.chat.service.TokenService;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    public final TokenService tokenService;

    public final FriendService friendService;

    public final UserRepository userRepository;

    @ApiOperation(value = "친구검색")
    @GetMapping("/search")
    @JsonView({JsonViewApi.class})
    public User searchUser(String userId) {
        return friendService.search(userId);
    }

    @ApiOperation(value = "친구추가")
    @PostMapping(value = "/add")
    public void addFriend(@RequestBody String friendId, @ModelAttributeMapping UserToken userToken) {
        friendService.addFriend(friendId, userToken);
    }

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

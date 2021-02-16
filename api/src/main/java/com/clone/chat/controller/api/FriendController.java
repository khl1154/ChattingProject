package com.clone.chat.controller.api;

import com.clone.chat.dto.FriendDto;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(FriendController.URI_PREFIX)
@Slf4j
@Api(tags = "친구")
public class FriendController {

    public static final String URI_PREFIX = ApiController.URI_PREFIX+"/friends";
    private final FriendService friendService;

    @ApiOperation(value = "친구추가")
    @PostMapping("/add")
    public void addFriend(@RequestBody @Valid FriendDto friendDto) {
        friendService.saveFriend(friendDto);
    }

    @ApiOperation(value = "친구목록")
    @GetMapping("/list")
    public Result<List<ProfileDto>> getFriendsProfile(String userId) {
        List<ProfileDto> friendsProfile = friendService.getList(userId);
        return new Result<List<ProfileDto>>(friendsProfile.size(), friendsProfile);
    }

    @Data
    @AllArgsConstructor
    @Builder
    static class Result<T> {
        private int count;
        private T profile;
    }
}

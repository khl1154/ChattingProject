package com.clone.chat.controller.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(FriendController.URI_PREFIX)
@Slf4j
@Api(tags = "친구")
public class FriendController {

    public static final String URI_PREFIX = ApiController.URI_PREFIX+"/friends";


}

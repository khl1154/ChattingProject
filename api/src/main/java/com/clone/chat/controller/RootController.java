package com.clone.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Api controller.
 */
@Slf4j
@RestController
public class RootController {

    @GetMapping(value={"","/"})
    public String index(){
        return "Welcome chat api server";
    }

}

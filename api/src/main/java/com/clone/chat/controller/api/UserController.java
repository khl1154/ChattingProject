package com.clone.chat.controller.api;

import com.clone.chat.domain.base.UserBase;
import com.clone.chat.model.ResponseForm;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.TokenService;
import com.clone.chat.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(UserController.URI_PREFIX)
@Slf4j
@Api(tags = "유저")
public class UserController {
    public static final String URI_PREFIX = ApiController.URI_PREFIX + "/users";

    @Autowired
    private final UserService userService;

    @Autowired
    private final TokenService tokenService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


//	@GetMapping("/friends")
//	public List<Friend> friends(@RequestParam("id") String id) {
//		return friendRepository.findByUserId(id);
//	}

    @GetMapping("/lists")
    public ResponseForm list(String id) {
        List<String> list = userService.getList(id);
        return new ResponseForm("list", list);
    }

    @PostMapping("/joins")
    public void join(@RequestBody UserBase dto) {
        userService.join(dto);
    }

    @GetMapping("/duplicate_check")
    public ResponseForm duplicate(String id) {
        userService.duplicateId(id);
        return new ResponseForm();
    }


    @GetMapping("/image")
    public void getImage(String id, HttpServletResponse response) throws IOException {
        File file = new File("src/main/resources/static/image/sample.png");
        Files.copy(file.toPath(), response.getOutputStream());
        //TODO thumnail
    }


}

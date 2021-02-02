package com.clone.chat.controller.api.users;

import com.clone.chat.controller.api.ApiController;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.model.ResponseForm;
import com.clone.chat.model.UserDto;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.TokenService;
import com.clone.chat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(UsersController.URI_PREFIX)
@Slf4j
@Api(tags = "유저")
public class UsersController {
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

    @ApiOperation(value = "회원가입")
    @PostMapping("/joins")
    public void join(UserDto userDto, @RequestPart(name = "file", required = false) MultipartFile file) {
        System.out.println("file.getContentType() = " + file.getContentType());
        userService.join(userDto, file);
    }

    @ApiOperation(value = "중복체크")
    @GetMapping("/duplicate_check")
    public CheckDuplicationResponse duplicate(String id) {
        boolean isDuplication = userService.duplicateId(id);
        return new CheckDuplicationResponse(isDuplication);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CheckDuplicationResponse {
        private boolean isDuplication;
    }


    @GetMapping("/image")
    public void getImage(String id, HttpServletResponse response) throws IOException {
        File file = new File("src/main/resources/static/image/sample.png");
        Files.copy(file.toPath(), response.getOutputStream());
        //TODO thumnail
    }


}

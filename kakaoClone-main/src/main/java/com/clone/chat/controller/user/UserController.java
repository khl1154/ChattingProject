package com.clone.chat.controller.user;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clone.chat.dto.UserDto;
import com.clone.chat.service.UserService;
import com.clone.chat.util.ResponseForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private final UserService userService;

	Logger logger = LoggerFactory.getLogger(UserController.class);





	
	@GetMapping("/list")
	public ResponseForm list(String id) {
		List<String> list = userService.getList(id);

		return new ResponseForm("list", list);
	}
	
	@PostMapping("/join")
	public ResponseForm join(@RequestBody UserDto dto) {
		userService.join(dto);

		return new ResponseForm();
	}
	
	@GetMapping("/duplicate_check")
	public ResponseForm duplicate(String id) {
		userService.duplicateId(id);
		
		return new ResponseForm();
	}

	@PostMapping("/login")
	public String login(@RequestBody UserDto dto, HttpServletResponse response) throws JsonProcessingException,UnsupportedEncodingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> resultMap = new HashMap<String,Object>();

		//해당 유저의 정보를 통해 고유한 토큰 생성
		String token = userService.create(dto.getId());
		Cookie cookieToken = new Cookie("Authorization", token);


		if("user1@daum.net".equals(dto.getId())&&"1234".equals(dto.getPw())){


			//클라이언트에 전송하기 위해 response 쿠키에 인증토큰을 담아준다.
			logger.info("loginSuccess");
			//response.setHeader("Authorization", token);
			response.addCookie(cookieToken);
			resultMap.put("user_id",dto.getId());
			resultMap.put("return","success");
		}else{
			logger.info("loginFail");
			resultMap.put("return","fail");
		}

		return new ObjectMapper().writeValueAsString(resultMap);
	}


	@GetMapping("/logout")
	public String login(String userId, @RequestHeader(value = "Authorization", required=false) String token) throws JsonProcessingException,UnsupportedEncodingException {

		return new ObjectMapper().writeValueAsString(userService.validate(token,userId));
	}




	@GetMapping("/image")
	public void getImage(String id, HttpServletResponse response) throws IOException {
		File file = new File("src/main/resources/static/image/sample.png");
		Files.copy(file.toPath(), response.getOutputStream());
		//TODO thumnail
	}

	
}

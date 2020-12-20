package com.clone.chat.controller.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.clone.chat.domain.base.UserBase;
import com.clone.chat.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clone.chat.model.ResponseForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(UserController.URI_PREFIX)
@Slf4j
@Api(tags = "유저")
public class UserController {
	public static final String URI_PREFIX = ApiController.URI_PREFIX+"/users";
	@Autowired
	private final UserService userService;


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

	@PostMapping("/login")
	public String login(@RequestBody UserBase dto, HttpServletResponse response) throws JsonProcessingException,UnsupportedEncodingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> resultMap = new HashMap<String,Object>();

		if("user1@daum.net".equals(dto.getId())&&"1234".equals(dto.getPassword())){


			//해당 유저의 정보를 통해 고유한 토큰 생성
			String token = userService.create(dto.getId());
			//클라이언트에 전송하기 위해 response 헤더에 인증토큰을 담아준다.
			log.info("loginSuccess");
			response.setHeader("Authorization", token);
			resultMap.put("user_id",dto.getId());
			resultMap.put("return","success");
		}else{
			log.info("loginFail");
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

package com.clone.chat.controller.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.clone.chat.code.MsgCode;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.dto.UserDto;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.exception.ErrorTrace;
import com.clone.chat.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import com.clone.chat.model.ResponseForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RequiredArgsConstructor
@RestController
@RequestMapping(UserController.URI_PREFIX)
@Slf4j
@Api(tags = "유저")
public class UserController {

	public static final String URI_PREFIX = ApiController.URI_PREFIX+"/users";
	@Autowired
	private final UserService userService;

	@PostMapping("/joins")
	public void join(@RequestBody UserDto userDto,@RequestParam(required = false) MultipartFile file) {
		userService.join(userDto, file);
	}

	@GetMapping("/duplicate_check")
	public CheckDuplicationResponse duplicate(String id) {
		boolean isDuplication = userService.duplicateId(id);
		return new CheckDuplicationResponse(isDuplication);
	}

	@GetMapping("/list")
	public List<String> getUsers() {
		return userService.getList();
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class CheckDuplicationResponse {
		private boolean isDuplication;
	}

}

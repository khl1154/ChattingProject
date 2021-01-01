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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clone.chat.model.ResponseForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
	public void join(UserDto userDto, MultipartFile file) {
		userService.join(userDto, file);
	}

	@GetMapping("/duplicate_check")
	public String duplicate(String id) {
		userService.duplicateId(id);
		return "success";
	}

	@GetMapping("/list")
	public List<String> getUsers() {
		return userService.getList();
	}
}

package com.clone.chat.controller.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.clone.chat.domain.User;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.model.Token;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.TokenService;
import com.clone.chat.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.clone.chat.model.ResponseForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(SecuritysController.URI_PREFIX)
@Slf4j
@Api(tags = "securitys")
public class SecuritysController {
	public static final String URI_PREFIX = ApiController.URI_PREFIX+"/securitys";


	@Autowired
	private final TokenService tokenService;


	@Autowired
	private UserRepository userRepository;


	@Autowired
	PasswordEncoder passwordEncoder;


	@PostMapping("/login")
	public Token login(@RequestBody UserBase requestUser, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {

		Optional<User> userOption = userRepository.findById(requestUser.getId());
		userOption.orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));
		User user = userOption.get();

		if (passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
			return new Token(tokenService.makeToken(user));
		} else {
			throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
		}
	}


//	@GetMapping("/logout")
//	public String login(String userId, @RequestHeader(value = "Authorization", required=false) String token) throws JsonProcessingException,UnsupportedEncodingException {
//		return new ObjectMapper().writeValueAsString(userService.validate(token,userId));
//	}

	
}
package com.clone.chat.service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.clone.chat.domain.File;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.exception.ErrorTrace;
import com.clone.chat.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.code.MsgCode;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final FileService fileService;
	private final PasswordEncoder passwordEncoder;


	@Transactional
	public void join(UserDto dto, MultipartFile file) {
		if(duplicateId(dto.getId()))
			throw new BusinessException(MsgCode.ERROR_DUPLICATED_ID, ErrorTrace.getName());
		User user = dto.toEntity();
		if(file != null) {
			File userFile = fileService.save(file);
			user.setFile(userFile);
		}
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

	public boolean duplicateId(String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent())
			return true;
		return false;
	}



	public List<String> getList(String id) {
		List<User> list = userRepository.findAll();
		List<String> response = new ArrayList<>();

		list.forEach(l -> {
			if (!l.getId().equals(id))
				response.add(l.getId());
		});


		return response;
	}














	public Map<String,Object> validate(String token, String userId) throws UnsupportedEncodingException{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//토큰을 복호화
		Claims claims = Jwts.parser().setSigningKey("secret".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		//[]replace
		String scope = String.valueOf(claims.get("scope")).replace("[","").replace("]","");

		//유저아이디와 해당 유저아이디의 토큰값이 일치하면 success 아니면 fail
		if(userId.equals(String.valueOf(scope))){
			log.info("logoutSuccess");
			resultMap.put("return","success");
		}else{
			log.info("logoutFail");
			resultMap.put("return","fail");
		}


		return resultMap;
	}




}

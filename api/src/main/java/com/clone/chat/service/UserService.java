package com.clone.chat.service;

import com.clone.chat.code.MsgCode;
import com.clone.chat.domain.File;
import com.clone.chat.domain.User;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.dto.UserDto;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.exception.ErrorTrace;
import com.clone.chat.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final FileService fileService;
	private final PasswordEncoder passwordEncoder;

	public User find(String userId) {
		return userRepository.findById(userId).get();
	}

	@Transactional
	public ProfileDto search(String userId) {
		Optional<User> findUser = userRepository.findByIdEquals(userId);
		ProfileDto profileDto = new ProfileDto();

		if(findUser.isPresent()) {
			User user = findUser.get();
			String filePath = "";
			if(user.getFile() != null) filePath = user.getFile().getFilePath();
			profileDto = new ProfileDto(user.getId(),filePath,user.getNickName(),user.getStatusMsg());
		}
		return profileDto;
	}

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

	public List<String> getList() {
		List<User> list = userRepository.findAll();
		List<String> response = new ArrayList<>();

		list.forEach(l -> {
				response.add(l.getId());
		});

		return response;
	}

}

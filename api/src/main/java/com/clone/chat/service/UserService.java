package com.clone.chat.service;

import com.clone.chat.code.MsgCode;
import com.clone.chat.domain.User;
import com.clone.chat.dto.UserDto;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.exception.ErrorTrace;
import com.clone.chat.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	@Transactional
	public void join(UserDto dto, MultipartFile file) {
		Optional<User> user = userRepository.findById(dto.getId());
		if (user.isPresent())
			throw new BusinessException(MsgCode.ERROR_DUPLICATED_ID, ErrorTrace.getName());
		Long fileId = 1L;
		if(!file.isEmpty()) {
			fileId = fileService.save(file);
		}
		dto.setFileId(fileId);
		userRepository.save((dto.toEntity()));
	}

	public void duplicateId(String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent())
			throw new BusinessException(MsgCode.ERROR_DUPLICATED_ID, ErrorTrace.getName());
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

	public String create(String userId) throws UnsupportedEncodingException {
		List<String> authList = new ArrayList();
		authList.add(userId);

		String jwt = Jwts.builder()
				//.setIssuer("Stormpath")
				//.setSubject("msilverman")
				.claim("scope", authList)
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
				.signWith(SignatureAlgorithm.HS256,
						"secret".getBytes("UTF-8"))
				.compact();

		System.out.println(jwt);

		return jwt;
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

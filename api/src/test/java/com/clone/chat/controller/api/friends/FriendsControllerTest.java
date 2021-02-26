package com.clone.chat.controller.api.friends;

import com.clone.chat.code.UserRole;
import com.clone.chat.config.security.jwt.JwtConfig;
import com.clone.chat.controller.api.ApiController;
import com.clone.chat.controller.api.friends.model.RequestAddFriend;
import com.clone.chat.domain.User;
import com.clone.chat.model.UserToken;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class FriendsControllerTest {

    public static final String URI_PREFIX = ApiController.URI_PREFIX + "/friends";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JwtConfig jwtConfig;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("친구검색 성공")
    @WithMockUser(roles = "USER")
    public void searchUser_success() throws Exception {
        // given
        User findUser = User.builder()
                .id("findUserId")
                .nickName("nickName")
                .password("password")
                .phone("010-1111-2222")
                .role(UserRole.USER)
                .build().map(User.class);
        userRepository.save(findUser);

        // when
        MvcResult mvcResult = mockMvc.perform(get(URI_PREFIX + "/search")
                .param("userId", "findUserId"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        User resultUser = mapper.readValue(content, User.class);
        assertEquals(findUser.getId(), resultUser.getId());
    }
}

package com.clone.chat.controller.api.anon;

import com.clone.chat.code.UserRole;
import com.clone.chat.controller.api.anon.model.RequestSignUp;
import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AnonApisControllerTest {

    public static final String URI_PREFIX = "/anon-apis";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    public void join_success() throws Exception {
        mockMvc.perform(post(URI_PREFIX + "/sign-up")
                .param("id", "newUserId")
                .param("password", "newUserPassword")
                .param("nickName", "newUserNickName")
                .param("phone", "newUserPhone"))
                .andExpect(status().isOk());

        Optional<User> findUser = userRepository.findById("newUserId");
        assertTrue(findUser.isPresent());
    }

    @Test
    @DisplayName("회원가입 유효성 실패")
    public void join_fail() throws Exception {
        mockMvc.perform(post(URI_PREFIX + "/sign-up")
                .param("id", "newUserId")
                .param("password", "newUserPassword")
                .param("nickName", "newUserNickName"))
                .andExpect(status().is4xxClientError());

        Optional<User> findUser = userRepository.findById("newUserId");
        assertFalse(findUser.isPresent());
    }

    @Test
    @DisplayName("회원찾기 성공")
    public void findUser_success() throws Exception {
        // given
        User findUser = User.builder()
                .id("findUserId")
                .nickName("nickName")
                .password("password")
                .phone("010-7365-0759")
                .role(UserRole.USER)
                .build().map(User.class);
        userRepository.save(findUser);

        // when
        MvcResult mvcResult = mockMvc.perform(get(URI_PREFIX + "/users/findUserId"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        User resultUser = mapper.readValue(content, User.class);
        assertEquals(findUser.getId(), resultUser.getId());
    }
}

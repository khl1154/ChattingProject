package com.clone.chat.controller.api.friends;

import com.clone.chat.code.UserRole;
import com.clone.chat.controller.api.ApiController;
import com.clone.chat.controller.api.friends.model.RequestAddFriend;
import com.clone.chat.domain.User;
import com.clone.chat.model.UserToken;
import com.clone.chat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.context.WebApplicationContext;

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
                .phone("010-7365-0759")
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

    @Test
    @DisplayName("친구추가 성공")
    @WithMockUser(roles = "USER")
    public void addFriend_success() throws Exception {
        //given
//        User user = User.builder().id("myId").build().map(User.class);
//        User friend = User.builder().id("friendId").build().map(User.class);
//        userRepository.save(user);
//        userRepository.save(friend);
//
//        RequestAddFriend requestAddFriend = new RequestAddFriend();
//        requestAddFriend.setId("friendId");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String request = objectMapper.writeValueAsString(requestAddFriend);
//        //when


        mockMvc.perform(post(URI_PREFIX + "/t")
//                .content(request)
                .param("id", "myId")
        )
                .andExpect(status().isOk());
        //then
//        assertTrue(user.getFriends().contains(friend));
    }

}

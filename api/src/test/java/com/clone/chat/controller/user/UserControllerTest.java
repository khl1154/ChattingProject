package com.clone.chat.controller.user;

import com.clone.chat.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;

//    @Autowired
//    private UserController userController;
//
//    @BeforeEach
//    public  void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController)
//                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
//                .build();
//    }

    @Test
    @Rollback(value = false)
    public void 테스트() throws Exception{

        //given
        UserDto userDto = new UserDto();
        userDto.setId("123");
        userDto.setPassword("1234");
        userDto.setPhone("010-1111-11111");
        MockHttpServletRequestBuilder builder = post("/user/join")
                .param("id", "123")
                .param("nickName", "김수로")
                .param("pw", "1234")
                .param("phone", "010-1111-2222");

        //when
        mockMvc.perform(builder);

        //then
    }

}

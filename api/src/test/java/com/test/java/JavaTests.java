package com.test.java;

import com.clone.chat.domain.User;
import com.clone.chat.domain.base.UserBase;

//@SpringBootTest
class JavaTests {

/*	@Test
	void contextLoads() {
	}*/

    public static void main(String[] args) {

        UserBase dto = new UserBase();
        dto.setId("id");
        User map = dto.map(User.class);
        System.out.printf("mapp"+ map.toString());

    }

}

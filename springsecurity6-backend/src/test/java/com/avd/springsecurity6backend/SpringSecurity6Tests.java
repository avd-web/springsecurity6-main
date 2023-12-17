package com.avd.springsecurity6backend;

import com.avd.springsecurity6backend.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurity6Tests {

	User user;

	@Test
	void contextLoads() {
	}

	@Test
	void hasUser(){
		User user = new User();
	}

}

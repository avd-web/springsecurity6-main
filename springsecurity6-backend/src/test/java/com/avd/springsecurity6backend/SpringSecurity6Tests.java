package com.avd.springsecurity6backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
//@ContextConfiguration(classes = YourWebSecurityConfigClass.class)
class SpringSecurity6Tests {

		@Autowired
		private MockMvc mockMvc;

		@Test
		@WithMockUser(username = "validUsername", password = "validPassword")
		public void testAuthentication() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/"))
					.andExpect(MockMvcResultMatchers.status().isOk());
		}

//	@Test
//	@WithMockUser(username = "UNvalidUsername", password = "validPassword")
//	public void testWrongUsername() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/"))
//				.andExpect(MockMvcResultMatchers.status().isForbidden());
//	}
//
//	@Test
//	@WithMockUser(username = "validUsername", password = "UNvalidPassword")
//	public void testWrongPassword() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/"))
//				.andExpect(MockMvcResultMatchers.status().isForbidden());
//	}

		@Test
		@WithMockUser(username = "usernameWithoutPermission", password = "validPassword")
		public void testAuthorization() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
					.andExpect(MockMvcResultMatchers.status().isForbidden());
		}
	}
package com.avd.springsecurity6backend.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes = YourWebSecurityConfigClass.class)
class SpringSecurity6Tests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void RunAllTests() throws Exception {
    }
}

        // Register a new user
//        this.mvc.perform(post("/api/v1/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"admin@mail.com\", \"password\":\"admin\"}"))
//                .andExpect(status().isOk());

        // Authenticate the new user
//        this.mvc.perform(post("/api/v1/auth/authenticate")
//                        .with(httpBasic("admin@mail.com", "admin"))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());

        //    @Test
//    @WithMockUser(username = "avd@mail.com", password = "1234")
//    public void testAuthentication() throws Exception {
//        mockMvc.perform(get("http://localhost:8080/api/v1/demo")
//                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdmRAbWFpbC5jb20iLCJpYXQiOjE3MDMzNTM4MzYsImV4cCI6MTcwMzM1NTI3Nn0.4oXdHAgDYecac4RucxG9cUDBdyaWlanDjNhljI6_s1c"))
//                .andExpect(status().isOk());
//
//    }

//    @Test
//    @WithUserDetails
//    @WithMockUser(firstname = "admin", lastname = "admin", email = "admin@mail.com", password = "admin", role = "ADMIN")
//    public void testAuthentication2() throws Exception {
//        mockMvc.perform(get("http://localhost:8080/api/v1/auth/register")
//                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdmRAbWFpbC5jb20iLCJpYXQiOjE3MDMzNTM4MzYsImV4cCI6MTcwMzM1NTI3Nn0.4oXdHAgDYecac4RucxG9cUDBdyaWlanDjNhljI6_s1c"))
//                .andExpect(status().isOk());
//
//    }


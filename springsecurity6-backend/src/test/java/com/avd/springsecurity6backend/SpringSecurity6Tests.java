package com.avd.springsecurity6backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes = YourWebSecurityConfigClass.class)
class SpringSecurity6Tests {

    //BASIC TESTS
    @Test
    public void winTest() {
    }

    @Autowired
    private MockMvc mockMvc;

    //TEST AUTHENTICATION
    @Test
    @WithMockUser(username = "avd@mail.com", password = "1234")
    public void testAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/demo-controller")
                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdmRAbWFpbC5jb20iLCJpYXQiOjE3MDMzNTM4MzYsImV4cCI6MTcwMzM1NTI3Nn0.4oXdHAgDYecac4RucxG9cUDBdyaWlanDjNhljI6_s1c"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

//    @Test
//    @WithMockUser(username = "12", password = "pass")
//    public void testInvalidUsername() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }

//    @Test
//    @WithMockUser(username = "avd", password = "  , ")
//    public void testInvalidPassword() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }

    //TEST AUTHORIZATION
//    @Test
//    @WithMockUser(username = "avd", password = "pass")
//    public void testAuthorization() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
}
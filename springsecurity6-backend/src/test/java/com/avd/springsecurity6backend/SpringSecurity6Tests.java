package com.avd.springsecurity6backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        mockMvc.perform(get("http://localhost:8080/api/v1/demo")
                        .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdmRAbWFpbC5jb20iLCJpYXQiOjE3MDMzNTM4MzYsImV4cCI6MTcwMzM1NTI3Nn0.4oXdHAgDYecac4RucxG9cUDBdyaWlanDjNhljI6_s1c"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnTheUserDetails() throws Exception {
        var expectedJson = "{\"name\" : \"testName\", \"age\" : 10, \"gender\" : \"M\"}";

        MvcResult result = mockMvc.perform(get("/api/user")
                .content("{\"name\" : \"testName\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualJson = result.getResponse().getContentAsString();

        assertEquals(expectedJson, actualJson); // or use any other library for comparison?
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
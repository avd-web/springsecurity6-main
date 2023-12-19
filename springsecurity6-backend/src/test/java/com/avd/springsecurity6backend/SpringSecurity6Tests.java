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
    @WithMockUser(username = "user", password = "99f4a362-a79a-4d5b-8672-56c3d8632d19")
    public void testAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "12", password = "pass")
    public void testInvalidUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "avd", password = "  , ")
    public void testInvalidPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    //TEST AUTHORIZATION
    @Test
    @WithMockUser(username = "avd", password = "pass")
    public void testAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
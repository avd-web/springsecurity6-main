package com.avd.springsecurity6backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
//@ContextConfiguration(classes = YourWebSecurityConfigClass.class)
class SpringSecurity6Tests {

    //BASIC TESTS
    @Test
    public void winTest() {
    }

    private MockMvc mockMvc;

    //TEST AUTHENTICATION
    @Test
    @WithMockUser(username = "avd", password = "pass")
    public void testAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
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
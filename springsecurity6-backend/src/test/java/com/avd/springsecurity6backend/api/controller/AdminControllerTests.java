package com.avd.springsecurity6backend.api.controller;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.demo.AdminController;
import com.avd.springsecurity6backend.token.TokenRepository;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
public class AdminControllerTests {

    @InjectMocks
    private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserDetailsService userDetailsService;

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/api/v1/admin").with(jwt().authorities(new SimpleGrantedAuthority("admin:read"))))
                .andExpect(status().isOk());
    }

    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/api/v1/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/api/v1/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/admin"))
                .andExpect(status().isOk());
    }
}
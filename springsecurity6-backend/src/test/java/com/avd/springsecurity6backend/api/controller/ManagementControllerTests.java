package com.avd.springsecurity6backend.api.controller;

import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.demo.ManagementController;
import com.avd.springsecurity6backend.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Implementation variant 2:
      (MockMvcBuilders.standaloneSetup + @InjectMocks) in ManagementControllerTests.java
          vs
      (@WebMvcTest(controllers = AdminController.class) + @Autowired) in AdminControllerTests.java
*/

public class ManagementControllerTests {
    @InjectMocks
    private ManagementController managementController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(managementController).build();
    }

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/api/v1/management").with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/api/v1/management").with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/api/v1/management").with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/management").with(jwt()))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testDelete() throws Exception {
//        mockMvc.perform(delete("/api/v1/management").with(jwt().authorities(new SimpleGrantedAuthority("admin:delete"))))
//                .andExpect(status().isOk());
//    }
}

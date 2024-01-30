package com.avd.springsecurity6backend.api.controller;

//import com.avd.springsecurity6backend.demo.AdminController;
import com.avd.springsecurity6backend.auth.AuthenticationController;
import com.avd.springsecurity6backend.auth.AuthenticationService;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.demo.AdminController;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.Role;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
public class AuthenticationControllerTests {

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

    @MockBean
    AuthenticationService authenticationService;


    @Test
    public void testGet() throws Exception {
        mockMvc.perform(post("/api/v1/auth/authenticate").with(jwt()))
                .andExpect(status().isOk());
    }
}

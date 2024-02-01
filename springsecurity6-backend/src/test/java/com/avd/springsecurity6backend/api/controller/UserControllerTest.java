package com.avd.springsecurity6backend.api.controller;

import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.ChangePasswordRequest;
import com.avd.springsecurity6backend.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private TokenRepository tokenRepository;

    @Test
    @WithMockUser(username = "john.doe", roles = {"USER"})
    public void testChangePassword() throws Exception {
        // Arrange
        String requestJson = "{ \"oldPassword\": \"oldPassword\", \"newPassword\": \"newPassword\" }";
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk());
        // Verify that the service method was called
        verify(userService).changePassword(any(ChangePasswordRequest.class), any(Principal.class));
    }
}

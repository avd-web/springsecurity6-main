package com.avd.springsecurity6backend.api.controller;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testRegister() throws Exception {

        String requestBody = "{\"username\" : \"user@mail.com\", \"password\" : \"ABCD1234\"}";
        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @Transactional
    public void testAuthenticate() throws Exception {

        User mockUser = TestUtil.createMockUser1();
        testEntityManager.persist(mockUser);

        String token = jwtService.generateToken(mockUser);
        String jsonRequest = "{ \"username\": \"user1@mail.com\", \"password\": \"password\" }";

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/auth/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                                .content(jsonRequest))
                .andExpect(status().isOk());

    }
}

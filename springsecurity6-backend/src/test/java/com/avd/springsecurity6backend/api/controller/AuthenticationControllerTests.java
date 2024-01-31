package com.avd.springsecurity6backend.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@RequiredArgsConstructor
public class AuthenticationControllerTests {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testAuthenticate() throws Exception {
        String requestBody = "{\"password\" : \"\"}";

        mockMvc.perform(post("/api/v1/auth/register")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect();

//        verify(authenticationService, times(1)).register(new RegisterRequest());
    }

//    //How do I create a Mock jwt?
//    @BeforeEach
//    private void setupJWT(){
//        String tokenString = jwtService.generateToken(TestUtil.createMockUser1();
//
//    }



//    @Test
//    public void testGet() throws Exception {
//        mockMvc.perform(post("/api/v1/auth/authenticate").with(jwt().jwt()))
//                .andExpect(status().isOk());
//    }
}

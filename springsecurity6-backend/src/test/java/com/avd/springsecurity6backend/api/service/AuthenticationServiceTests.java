package com.avd.springsecurity6backend.api.service;
import com.avd.springsecurity6backend.auth.AuthenticationResponse;
import com.avd.springsecurity6backend.auth.AuthenticationService;
import com.avd.springsecurity6backend.auth.RegisterRequest;
import com.avd.springsecurity6backend.config.JwtService;
import com.avd.springsecurity6backend.token.Token;
import com.avd.springsecurity6backend.token.TokenRepository;
import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;
import com.avd.springsecurity6backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john.doe", "password", Role.USER);
        User mockUser = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .username(registerRequest.getUsername())
                .password("encodedPassword") // Mocking encoded password
                .role(registerRequest.getRole())
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockAccessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("mockRefreshToken");

        // When
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        // Then
        assertEquals("mockAccessToken", authenticationResponse.getAccessToken());
        assertEquals("mockRefreshToken", authenticationResponse.getRefreshToken());

        // Verify that necessary methods were called
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
        verify(jwtService).generateRefreshToken(any(User.class));
        verify(tokenRepository).save(any(Token.class)); // Assuming there is a method saveUserToken in your class
    }
//
//
//    @Test
//    public void testAuthenticate() {
//        // Arrange
//        final User user1 = TestUtil.createMockUser1();
//
//        AuthenticationRequest request = new AuthenticationRequest();
//        request.setUsername(user1.getUsername());
//        request.setPassword(user1.getPassword());
//
//        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user1));
//        when(jwtService.generateToken(user1)).thenReturn("jwtToken");
//        when(jwtService.generateRefreshToken(user1)).thenReturn("refreshToken");
//
//        // Act
//        AuthenticationResponse response = authenticationService.authenticate(request);
//
//        // Assert
//        assertEquals("jwtToken", response.getAccessToken());
//        assertEquals("refreshToken", response.getRefreshToken());
//    }
//
//    @Test
//    public void testRefreshToken() throws IOException {
//        // Arrange
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        String authHeader = "Bearer jwtToken";
//
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
//
//        User user = new User();
//        user.setUsername("johndoe");
//        user.setPassword("password");
//
//        when(jwtService.extractUsername(authHeader.substring(7))).thenReturn(user.getUsername());
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
//        when(jwtService.isTokenValid(authHeader.substring(7), user)).thenReturn(true);
//        when(jwtService.generateToken(user)).thenReturn("newJwtToken");
//
//        // Create a ByteArrayOutputStream and a ServletOutputStream that writes to it
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ServletOutputStream servletOutputStream = new ServletOutputStream() {
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//
//            @Override
//            public void setWriteListener(WriteListener writeListener) {
//
//            }
//
//            @Override
//            public void write(int b) throws IOException {
//                byteArrayOutputStream.write(b);
//            }
//        };
//
//        when(response.getOutputStream()).thenReturn(servletOutputStream);
//
//        // Act
//        authenticationService.refreshToken(request, response);
//
//        // Assert
//        String responseOutput = byteArrayOutputStream.toString();
//        assertTrue(responseOutput.contains("newJwtToken"));
//    }


}
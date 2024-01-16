package com.avd.springsecurity6backend.api.repository;

import com.avd.springsecurity6backend.token.Token;
import com.avd.springsecurity6backend.token.TokenRepository;
//import org.junit.jupiter.api.Assertions;
import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;
import com.avd.springsecurity6backend.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TokenRepositoryTest {

    //Arrange testable input-values for user1
    String TestEmail = "user@mail.com";
    String TestFirstname = "userFirstname";
    String TestLastname = "userLastname";
    String TestPassword = "password";
    Role TestRole = Role.USER;

    //Arrange testable input-values for user2
    String TestEmail2 = "user2@mail.com";
    String TestFirstname2 = "user2Firstname";
    String TestLastname2 = "user2Lastname";
    String TestPassword2 = "password";
    Role TestRole2 = Role.USER;

    //Arrange building a testable: user1
    User user1 = User.builder()
            .username(TestEmail)
            .firstname(TestFirstname)
            .lastname(TestLastname)
            .password(TestPassword)
            .role(TestRole)
            .build();

    //Arrange building testUser2
    User user2 = User.builder()
            .username(TestEmail2)
            .firstname(TestFirstname2)
            .lastname(TestLastname2)
            .password(TestPassword2)
            .role(TestRole2)
            .build();

    //Arrange building token with user1
    Token token = Token.builder().user(user1).build();

    @Autowired
    private UserRepository userRepository;

    private TokenRepository tokenRepository;

    @Autowired
    public TokenRepositoryTest(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Test
    public void TokenRepository_SaveToken_ReturnSavedToken() {

        //Act
        userRepository.save(user1);
        Token savedToken = tokenRepository.save(token);

        //Assert
        Assertions.assertThat(savedToken).isNotNull();
        Assertions.assertThat(savedToken.expired).isFalse();
        Assertions.assertThat(savedToken.revoked).isFalse();
        Assertions.assertThat(savedToken.user.getUsername()).isEqualTo(TestEmail);
    }

    @Test
    public void TokenRepository_FindByToken_ReturnUser() {

        //Act
        userRepository.save(user1);
        Token savedToken = tokenRepository.save(token);

        //Assert
        Assertions.assertThat(tokenRepository.findByToken(savedToken.token)).isNotNull();

    }

    @Test
    public void TokenRepository_FindAllValidTokenByUser_ReturnToken() {

        //Arrange
        Token token2 = Token.builder().user(user1).build();
        Token token3 = Token.builder().user(user1).build();
        Token token4 = Token.builder().user(user2).build(); //create a different token using user2

        //Act
        userRepository.save(user1);
        userRepository.save(user2);

        tokenRepository.save(token);
        tokenRepository.save(token2);
        tokenRepository.save(token3);
        tokenRepository.save(token4);
        List<Token> tokenList = tokenRepository.findAllValidTokenByUser(user1.getId());

        //Assert
        Assertions.assertThat(userRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(tokenList).isNotNull();
        Assertions.assertThat(tokenList.size()).isEqualTo(3);

    }

}

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

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private TokenRepository tokenRepository;

    @Autowired
    public TokenRepositoryTest(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Test
    public void TokenRepository_SaveToken_ReturnSavedToken() {

        //Arrange
        User user = User.builder()
                .username("user@mail.com")
                .firstname("user").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

        Token token = Token.builder().user(user).build();

        //Act
        userRepository.save(user);
        Token savedToken = tokenRepository.save(token);

        //Assert
        Assertions.assertThat(savedToken).isNotNull();
        Assertions.assertThat(savedToken.expired).isFalse();
        Assertions.assertThat(savedToken.revoked).isFalse();
        Assertions.assertThat(savedToken.user.getUsername()).isEqualTo("user@mail.com");
    }

    @Test
    public void TokenRepository_FindByToken_ReturnUser() {

        //Arrange

        //Act

        //Assert

    }

    @Test
    public void TokenRepository_FindAllValidTokenByUser_ReturnToken() {

        //Arrange
        User user1 = User.builder()
                .username("user1@mail.com")
                .firstname("user1").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .username("user2@mail.com")
                .firstname("user2").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

        //Act
        userRepository.save(user1);
        userRepository.save(user2);

        Token token1 = Token.builder().user(user1).build();
        Token token2 = Token.builder().user(user1).build();
        Token token3 = Token.builder().user(user1).build();
        Token token4 = Token.builder().user(user2).build(); //create a 2nd, different user.

        tokenRepository.save(token1);
        tokenRepository.save(token2);
        tokenRepository.save(token3);
        tokenRepository.save(token4);
        List<Token> tokenList = tokenRepository.findAllValidTokenByUser(user1.getId());

        //Assert
        Assertions.assertThat(userRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(tokenList).isNotNull();
        Assertions.assertThat(tokenList).isNotNull();
        Assertions.assertThat(tokenList.size()).isEqualTo(3);

    }

}

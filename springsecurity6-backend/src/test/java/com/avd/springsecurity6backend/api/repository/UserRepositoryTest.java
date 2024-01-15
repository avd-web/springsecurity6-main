package com.avd.springsecurity6backend.api.repository;

import com.avd.springsecurity6backend.user.Role;
import com.avd.springsecurity6backend.user.User;
import com.avd.springsecurity6backend.user.UserRepository;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser() {

        //Arrange
        User user = User.builder()
                .username("user@mail.com")
                .firstname("user").lastname("user").build();

        //Act
        User savedUser = userRepository.save(user);

        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThenOneUser(){

        //Arrange
        User user1 = User.builder()
                .username("user1@mail.com")
                .firstname("user1").lastname("user").build();
        User user2 = User.builder()
                .username("user2@mail.com")
                .firstname("user2").lastname("user").build();

        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> userList = userRepository.findAll();

        //Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_FindById_ReturnUser(){

        //Arrange
        User user = User.builder()
                .username("user@mail.com")
                .firstname("user").lastname("user").build();

        userRepository.save(user);

        //Act
        User userList = userRepository.findById(user.getId()).stream().findFirst().orElse(null);

        //Assert
        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByRole_ReturnUserNotNull(){

        //Arrange
        User user = User.builder()
                .username("user@mail.com")
                .role(Role.USER)
                .firstname("user").lastname("user").build();

        userRepository.save(user);

        //Act
        User userList = userRepository.findByRole(Role.USER).stream().findFirst().orElse(null);

        //Assert
        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByRole_Return2Users(){

        //Arrange
        User user1 = User.builder()
                .username("user1@mail.com")
                .role(Role.USER)
                .firstname("user1").lastname("user").build();
        User user2 = User.builder()
                .username("user2@mail.com")
                .role(Role.USER)
                .firstname("user2").lastname("user").build();

        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> userList = userRepository.findAllByRole(Role.USER);

        //Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }


}

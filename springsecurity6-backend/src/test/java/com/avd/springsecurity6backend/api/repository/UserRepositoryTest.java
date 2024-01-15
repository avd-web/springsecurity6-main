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
import java.util.Optional;

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
                .firstname("user").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

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
                .username("user@mail.com")
                .firstname("user").lastname("user")
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
                .firstname("user").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

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
                .firstname("user").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

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
                .username("user@mail.com")
                .firstname("user").lastname("user")
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
        List<User> userList = userRepository.findAllByRole(Role.USER);

        //Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_UpdateUser_ReturnChangedUser(){

        //Arrange
        User user = User.builder()
                .username("user@mail.com")
                .firstname("user").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

        //Act
        userRepository.save(user);

        User userSaved = userRepository.findById(user.getId()).stream().findFirst().orElse(null);
        assert userSaved != null;
        userSaved.setPassword("changedPassword");
        userSaved.setUsername("changedUser@mail.com");
        userSaved.setFirstname("FirstName");
        userSaved.setLastname("LastName");

        User updatedUser = userRepository.save(userSaved);

        //Assert
        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("changedPassword");
        Assertions.assertThat(updatedUser.getUsername()).isEqualTo("changedUser@mail.com");
        Assertions.assertThat(updatedUser.getFirstname()).isEqualTo("FirstName");
        Assertions.assertThat(updatedUser.getLastname()).isEqualTo("LastName");
    }

    @Test
    public void UserRepository_UserDelete_ReturnUserIsEmpty(){

        //Arrange
        User user = User.builder()
                .username("user@mail.com")
                .firstname("user").lastname("user")
                .password("password")
                .role(Role.USER)
                .build();

        //Act
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        Optional<User> userReturn = userRepository.findById(user.getId());

        //Assert
        Assertions.assertThat(userReturn).isEmpty();
    }

}

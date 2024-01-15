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

    //Arrange test input for user1
    String TestEmail = "user@mail.com";
    String TestFirstname = "userFirstname";
    String TestLastname = "userLastname";
    String TestPassword = "password";
    Role TestRole = Role.USER;

    //Arrange test input for user2
    String TestEmail2 = "user2@mail.com";
    String TestFirstname2 = "user2Firstname";
    String TestLastname2 = "user2Lastname";
    String TestPassword2 = "password";
    Role TestRole2 = Role.USER;

    //Arrange building testUser1
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

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser() {

        //Act
        User savedUser = userRepository.save(user1);

        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(TestEmail);
        Assertions.assertThat(savedUser.getFirstname()).isEqualTo(TestFirstname);
        Assertions.assertThat(savedUser.getLastname()).isEqualTo(TestLastname);
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(TestPassword);
        Assertions.assertThat(savedUser.getRole()).isEqualTo(TestRole);

    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThenOneUser(){

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

        //Act
        userRepository.save(user1);
        User userList = userRepository.findById(user1.getId()).stream().findFirst().orElse(null);

        //Assert
        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByRole_ReturnUserNotNull(){

        //Act
        userRepository.save(user1);
        User userList = userRepository.findByRole(Role.USER).stream().findFirst().orElse(null);

        //Assert
        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByRole_Return2Users(){

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

        //Act
        userRepository.save(user1);

        User userSaved = userRepository.findById(user1.getId()).stream().findFirst().orElse(null);
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

        //Act
        userRepository.save(user1);
        userRepository.deleteById(user1.getId());
        Optional<User> userReturn = userRepository.findById(user1.getId());

        //Assert
        Assertions.assertThat(userReturn).isEmpty();
    }

}

package com.avd.springsecurity6backend.api.repository;

import com.avd.springsecurity6backend.api.TestUtil;
import com.avd.springsecurity6backend.token.Token;
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
public class UserRepositoryTests {

    private final User user1 = TestUtil.createMockUser1();
    private final User user2 = TestUtil.createMockUser1();
    private final Token token = Token.builder().user(user1).build();

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Save_ReturnSavedUser() {
        //Act
        User savedUser = userRepository.save(user1);
        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user1.getUsername());
        Assertions.assertThat(savedUser.getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(savedUser.getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(savedUser.getRole()).isEqualTo(user1.getRole());

    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThenOneUser() {
        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> userList = userRepository.findAll();
        //Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_FindById_ReturnUser() {
        //Act
        userRepository.save(user1);
        User userList = userRepository.findById(user1.getId()).stream().findFirst().orElse(null);
        //Assert
        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByRole_ReturnUserNotNull() {
        //Act
        userRepository.save(user1);
        User userList = userRepository.findByRole(Role.USER).stream().findFirst().orElse(null);
        //Assert
        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByRole_Return2Users() {
        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> userList = userRepository.findAllByRole(Role.USER);
        //Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_UpdateUser_ReturnChangedUser() {
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
    public void UserRepository_UserDelete_ReturnUserIsEmpty() {
        //Act
        userRepository.save(user1);
        userRepository.deleteById(user1.getId());
        Optional<User> userReturn = userRepository.findById(user1.getId());
        //Assert
        Assertions.assertThat(userReturn).isEmpty();
    }

}

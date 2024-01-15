package com.avd.springsecurity6backend.user;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByRole(Role role);

    List<User> findAllByRole(Role role);
}

package com.avd.springsecurity6backend.auth;


import com.avd.springsecurity6backend.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Must be between 3 and 20 characters")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Must be at least 8 characters")
    private String password;
    private Role role;
}


package com.kindora.kindora.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank(message = "Please enter your full name.")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String fullName;

    @NotBlank(message = "Please enter your email.")
    @Email(message = "Please enter a valid email address.")
    @Size(max = 150, message = "Email is too long.")
    private String email;

    @NotBlank(message = "Please enter a password.")
    @Size(min = 8, max = 72,
          message = "Password must contain 8 to 72 characters.")
    private String password;

    @NotBlank(message = "Please choose an account type.")
    private String role;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

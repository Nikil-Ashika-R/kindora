
package com.kindora.kindora.controller;

import com.kindora.kindora.dto.RegistrationForm;
import com.kindora.kindora.model.AppUser;
import com.kindora.kindora.repository.AppUserRepository;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistration(
            @RequestParam(required = false) String success,
            Model model) {

        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("registered", success != null);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid RegistrationForm form,
            BindingResult result,
            Model model) {

        String role = form.getRole() == null
                ? "" : form.getRole().trim().toUpperCase();

        if (!role.equals("DONOR") && !role.equals("NGO")) {
            result.rejectValue(
                    "role", "invalid.role",
                    "Choose Donor or NGO.");
        }

        String email = form.getEmail() == null
                ? "" : form.getEmail().trim().toLowerCase();

        if (!email.isEmpty()
                && userRepository.existsByEmailIgnoreCase(email)) {
            result.rejectValue(
                    "email", "duplicate.email",
                    "This email is already registered.");
        }

        if (result.hasErrors()) {
            model.addAttribute("registered", false);
            return "register";
        }

        AppUser user = new AppUser();
        user.setFullName(form.getFullName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return "redirect:/register?success";
    }
}


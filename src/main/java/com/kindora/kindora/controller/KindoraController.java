
package com.kindora.kindora.controller;

import com.kindora.kindora.model.AppUser;
import com.kindora.kindora.model.FoodDonation;
import com.kindora.kindora.repository.AppUserRepository;
import com.kindora.kindora.repository.FoodDonationRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class KindoraController {

    private final FoodDonationRepository repository;
    private final AppUserRepository userRepository;

    public KindoraController(
            FoodDonationRepository repository,
            AppUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("foodDonation", new FoodDonation());
        model.addAttribute("donations",
                repository.findByStatus("AVAILABLE"));
        model.addAttribute("role",
                getCurrentUser(authentication).getRole());
        model.addAttribute("allDonations", repository.findAll());
        return "index";
    }

    @PostMapping("/donate")
    public String donate(
            @ModelAttribute("foodDonation") FoodDonation foodDonation,
            Authentication authentication) {

        AppUser user = getCurrentUser(authentication);

        if (!"DONOR".equals(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Only donors can post donations.");
        }

        //foodDonation.setId(null);
        foodDonation.setClaimedBy(null);
        foodDonation.setStatus("AVAILABLE");
        repository.save(foodDonation);

        return "redirect:/";
    }

    @PostMapping("/claim/{id}")
    public String claim(
            @PathVariable Long id,
            Authentication authentication) {

        AppUser user = getCurrentUser(authentication);

        if (!"NGO".equals(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Only NGOs can claim donations.");
        }

        FoodDonation donation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Donation not found."));

        if (!"AVAILABLE".equals(donation.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "This donation is no longer available.");
        }

        donation.setStatus("CLAIMED");
        donation.setClaimedBy(user.getEmail());
        repository.save(donation);

        return "redirect:/";
    }

    private AppUser getCurrentUser(Authentication authentication) {
        return userRepository.findByEmailIgnoreCase(
                authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found."));
    }
}

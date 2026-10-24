
package com.kindora.kindora.repository;

import com.kindora.kindora.model.FoodDonation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodDonationRepository
        extends JpaRepository<FoodDonation, Long> {

    List<FoodDonation> findByStatus(String status);
}

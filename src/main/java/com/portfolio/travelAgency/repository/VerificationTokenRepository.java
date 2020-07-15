package com.portfolio.travelAgency.repository;


import com.portfolio.travelAgency.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;


public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

}

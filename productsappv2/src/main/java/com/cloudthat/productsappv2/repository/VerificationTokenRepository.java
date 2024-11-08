
package com.cloudthat.productsappv2.repository;


import com.cloudthat.productsappv2.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String oldToken);
    VerificationToken findByUserId(Long id);

}

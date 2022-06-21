
package com.mindhub.HomeBanking.Repositories;

import com.mindhub.HomeBanking.models.CardCreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CreditCardLimitRepository extends JpaRepository<CardCreditLimit, Long> {
}


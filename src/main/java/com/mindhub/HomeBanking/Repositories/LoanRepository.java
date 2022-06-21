package com.mindhub.HomeBanking.Repositories;

import com.mindhub.HomeBanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//REST conjunto de pautas de diseño para desarrollar API escalables basadas en la web.
@RepositoryRestResource//nos crea los controladores para el repositorio.
// trae las accounts que estan guardadas en la base de datos
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findById (long Id);
}

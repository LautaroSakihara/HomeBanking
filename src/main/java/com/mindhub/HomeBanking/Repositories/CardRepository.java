package com.mindhub.HomeBanking.Repositories;


import com.mindhub.HomeBanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//REST conjunto de pautas de diseño para desarrollar API escalables basadas en la web.
@RepositoryRestResource//nos crea los controladores para el repositorio.
// trae las cards que estan guardadas en la base de datos
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findById (long Id);
    Card findByNumber(String number);
}

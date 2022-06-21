package com.mindhub.HomeBanking.Repositories;

import com.mindhub.HomeBanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//REST conjunto de pautas de diseño para desarrollar API escalables basadas en la web.
@RepositoryRestResource//nos crea los controladores para el repositorio.
// trae las accounts que estan guardadas en la base de datos
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);

}

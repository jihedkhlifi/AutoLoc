package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolocapi.domain.Client;
import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);
}

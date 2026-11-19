package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolocapi.domain.Contrat;
import java.time.LocalDate;
import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    List<Contrat> findByDateSignature(LocalDate dateSignature);
}

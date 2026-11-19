package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolocapi.domain.Vehicule;
import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByImmatriculationIgnoreCase(String immatriculation);
}

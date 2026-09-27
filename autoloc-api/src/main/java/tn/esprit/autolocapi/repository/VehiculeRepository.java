package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolocapi.domain.Vehicule;

// <Vehicule, Long> = <entité, type de l'@Id>
// save(), findAll(), findById(), count()... sont fournis automatiquement
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}

package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolocapi.domain.Maintenance;
import java.util.List;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByDescriptionIgnoreCase(String description);
}

package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolocapi.domain.Reservation;
import java.time.LocalDate;
import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);
}

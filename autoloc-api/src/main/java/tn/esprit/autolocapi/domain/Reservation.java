package tn.esprit.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    // * ---- 1 Vehicule
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    // * ---- 1 Client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // 1 ---- 1 Contrat (côté inverse)
    @OneToOne(mappedBy = "reservation")
    private Contrat contrat;
}

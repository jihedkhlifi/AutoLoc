package tn.esprit.autolocapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity                          // cette classe devient la table...
@Table(name = "vehicule")        // ...nommée "vehicule"
@Getter                          // Lombok ciblé : PAS de @Data
@Setter                          // (@Data générerait equals/hashCode/toString
@NoArgsConstructor               //  -> boucles infinies avec les associations de l'Atelier 2)
@AllArgsConstructor
public class Vehicule {

    @Id                                                   // clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // AUTO_INCREMENT MySQL
    private Long idVehicule;                              // colonne id_vehicule

    // NOT NULL + UNIQUE + VARCHAR(20) : deux véhicules ne peuvent pas avoir la même plaque
    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;

    @Enumerated(EnumType.STRING)          // stocke "SUV" et non un numéro
    @Column(nullable = false, length = 20)
    private CategorieVehicule categorie;

    // BigDecimal pour l'argent (Double fait des erreurs d'arrondi)
    // precision 10, scale 2 -> DECIMAL(10,2) : ex. 12345678.90
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutVehicule statut;
}

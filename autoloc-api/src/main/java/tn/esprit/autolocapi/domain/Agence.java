package tn.esprit.autolocapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    // Agence 1 ---- * Employe
    @OneToMany(mappedBy = "agence")
    @JsonIgnore
    private List<Employe> employes = new ArrayList<>();

    // Agence 1 ---- * Vehicule
    @OneToMany(mappedBy = "agence")
    @JsonIgnore
    private List<Vehicule> vehicules = new ArrayList<>();
}

package tn.esprit.autolocapi.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.autolocapi.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Corps de la requete "creer une reservation et son contrat associe".
 * statut est facultatif (CONFIRMEE par defaut) ; montantTotal est facultatif
 * (calcule a partir du tarif journalier du vehicule s'il est absent).
 */
public record ReservationWithContratRequestDTO(
        // --- reservation
        @NotNull(message = "La date de debut est obligatoire") LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire") LocalDate dateFin,
        StatutReservation statut,
        @NotNull(message = "L'identifiant du vehicule est obligatoire")
        @Positive(message = "L'identifiant du vehicule doit etre positif") Long vehiculeId,
        @NotNull(message = "L'identifiant du client est obligatoire")
        @Positive(message = "L'identifiant du client doit etre positif") Long clientId,
        // --- contrat
        @NotNull(message = "La date de signature du contrat est obligatoire") LocalDate dateSignature,
        @DecimalMin(value = "0.0", message = "Le montant total ne peut pas etre negatif") BigDecimal montantTotal,
        boolean valide
) {
    @AssertTrue(message = "La date de fin doit etre posterieure ou egale a la date de debut")
    public boolean isPeriodeValide() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }

    @AssertTrue(message = "Le contrat doit etre signe au plus tard le jour du debut de la reservation")
    public boolean isSignatureValide() {
        return dateSignature == null || dateDebut == null || !dateSignature.isAfter(dateDebut);
    }
}

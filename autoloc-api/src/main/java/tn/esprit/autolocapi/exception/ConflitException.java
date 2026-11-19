package tn.esprit.autolocapi.exception;

/** Regle metier violee par l'etat actuel des donnees (ex. affectation deja faite) : HTTP 409. */
public class ConflitException extends RuntimeException {

    public ConflitException(String message) {
        super(message);
    }
}

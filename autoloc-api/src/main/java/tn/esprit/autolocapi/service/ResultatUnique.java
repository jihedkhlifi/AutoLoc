package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Les services d'affectation recherchent des entites par un attribut non unique
 * (nom/prenom, description, dates...). Cette classe exige exactement un resultat.
 */
final class ResultatUnique {

    private ResultatUnique() {
    }

    static <T> T exiger(List<T> resultats, String description) {
        if (resultats.isEmpty()) {
            throw new ResourceNotFoundException("Aucun(e) " + description);
        }
        if (resultats.size() > 1) {
            throw new IllegalArgumentException("Plusieurs resultats pour " + description
                    + " : critere ambigu, impossible de choisir");
        }
        return resultats.get(0);
    }
}

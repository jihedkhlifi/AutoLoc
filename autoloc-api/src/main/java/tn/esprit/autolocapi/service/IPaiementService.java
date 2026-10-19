package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.domain.Paiement;

import java.util.List;

/**
 * Lecture seule : la création, la modification et la suppression
 * des paiements passent par le Contrat (composition).
 */
public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}

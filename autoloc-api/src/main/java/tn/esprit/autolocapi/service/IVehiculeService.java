package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule);

    Vehicule updateVehicule(Vehicule vehicule);

    Vehicule retrieveVehicule(Long idVehicule);

    List<Vehicule> retrieveAllVehicules();

    void removeVehicule(Long idVehicule);
}

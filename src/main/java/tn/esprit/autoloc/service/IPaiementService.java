package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

// Le paiement est en lecture seule
public interface IPaiementService {
    Paiement findById(Long id);

    List<Paiement> findAll();
}

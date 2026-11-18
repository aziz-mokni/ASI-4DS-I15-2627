package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService extends IGenericService<Agence, Long> {
    boolean hasVehicules(Long id);

    default Agence updateAvecEmployes(Long id, Agence agence) {
        throw new UnsupportedOperationException();
    }
}

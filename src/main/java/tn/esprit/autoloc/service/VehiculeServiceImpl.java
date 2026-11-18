package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.IOffreStrategy;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    private final Map<String, IOffreStrategy> strategies;
    private final IAgenceRepository agenceRepository;
    private final IEquipementService equipementService;

    public VehiculeServiceImpl(IVehiculeRepository repository, Map<String, IOffreStrategy> strategies,
                               IAgenceRepository agenceRepository, IEquipementService equipementService) {
        super(repository);
        this.strategies = strategies;
        this.agenceRepository = agenceRepository;
        this.equipementService = equipementService;
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule vehicule) {
        return vehicule.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule vehicule, boolean isNew) {
        return vehicule != null
                && Utils.isNotEmpty(vehicule.getImmatriculation())
                && Utils.isNotEmpty(vehicule.getMarque())
                && Utils.isNotEmpty(vehicule.getModele())
                && vehicule.getCategorie() != null
                && vehicule.getStatut() != null
                && vehicule.getTarifJournalier() != null
                && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public double calculTarif(Vehicule vehicule, int nbJours, VehiculeOffre offre) {
        return strategies.get(offre.name()).calculer(vehicule.getTarifJournalier().doubleValue(), nbJours);
    }

    @Override
    @Transactional
    public void desaffecterAgence(Long idVehicule) {
        findById(idVehicule).setAgence(null);
    }

    @Override
    @Transactional
    public void affecterAgence(Long idVehicule, Long idAgence) {
        findById(idVehicule).setAgence(agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(idAgence))));
    }

    @Override
    @Transactional
    public void affecterEquipement(Long idVehicule, Long idEquipement) {
        findById(idVehicule).getEquipements().add(equipementService.findById(idEquipement));
    }
}

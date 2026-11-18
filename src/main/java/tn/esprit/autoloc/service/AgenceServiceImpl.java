package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService, ISimulationService {

    private final IVehiculeService vehiculeService;
    private final IEmployeService employeService;

    public AgenceServiceImpl(IAgenceRepository repository, IVehiculeService vehiculeService, IEmployeService employeService) {
        super(repository);
        this.vehiculeService = vehiculeService;
        this.employeService = employeService;
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence agence) {
        return agence.getIdAgence();
    }

    @Override
    protected void setFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setVille(source.getVille());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
    }

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return agence != null
                && Utils.isNotEmpty(agence.getNom())
                && Utils.isNotEmpty(agence.getVille())
                && Utils.isNotEmpty(agence.getAdresse());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Agence agence = findById(id);
        agence.getVehicules().forEach(v -> v.setAgence(null));
        agence.getEmployes().forEach(e -> e.setAgence(null));
        repository.delete(agence);
    }

    // Tout ou rien : une exception (ex. employe invalide) annule la mise a jour et les insertions deja faites
    @Override
    @Transactional
    public Agence updateAvecEmployes(Long id, Agence agence) {
        List<Employe> nouveaux = agence.getEmployes();
        Agence existing = update(id, agence);
        for (Employe employe : nouveaux) {
            existing.addEmploye(employe);
            employeService.create(employe);
        }
        existing.setEffectif(existing.getEmployes().size());
        return repository.save(existing);
    }

    @Override
    public boolean hasVehicules(Long id) {
        return !findById(id).getVehicules().isEmpty();
    }

    @Override
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        StringBuilder rapport = new StringBuilder();
        rapport.append("id: " + agence.getIdAgence() + ", nom: " + agence.getNom() + ", tel: " + agence.getTelephone() + "\n");

        double total =  0;

        for (Vehicule v: agence.getVehicules()) {
            total += vehiculeService.calculTarif(v, nbJours, offre);
        }

        rapport.append("total: " + "%.2f".formatted(total) + "\n");
        return rapport.toString();
    }
}

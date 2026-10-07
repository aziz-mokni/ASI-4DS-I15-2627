package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AgenceTests {
    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "CrudRepository (basic)");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "JpaRepository (full)");
    }

    @Test
    public void loadSortedAgences() {
        StringBuilder sb = new StringBuilder();
        for (Agence agence : fullAgenceRepository.findAll(Sort.by(Sort.Direction.DESC, "idAgence"))) {
            sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom())
                    .append(" | ").append(agence.getVille()).append(" | ").append(agence.getAdresse())
                    .append(" | ").append(agence.getTelephone());
        }
        fail(sb.toString());
    }

    @Test
    public void loadPagedAgences() {
        StringBuilder sb = new StringBuilder();
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "idAgence"));
        Page<Agence> page;
        do {
            page = fullAgenceRepository.findAll(pageable);
            sb.append("\nPage ").append(page.getNumber() + 1).append(" / ").append(page.getTotalPages());
            for (Agence agence : page) {
                sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom())
                        .append(" | ").append(agence.getVille()).append(" | ").append(agence.getAdresse())
                        .append(" | ").append(agence.getTelephone());
            }
            pageable = page.nextPageable();
        } while (page.hasNext());
        fail(sb.toString());
    }

    private void addAgence(CrudRepository<Agence, Long> repository) {
        // abs : (int) currentTimeMillis peut etre negatif, et immatriculation est limitee a 20 caracteres
        int suffix = Math.abs((int) System.currentTimeMillis());

        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        agence.getVehicules().add(new Vehicule(null, "785414TU96" + suffix, "Isuzu", "DMax",
                CategorieVehicule.SUV, new BigDecimal("100"), StatutVehicule.MAINTENANCE, agence, new ArrayList<>(), new ArrayList<>()));
        agence.getVehicules().add(new Vehicule(null, "785414TU95" + suffix, "Toyota", "Yaris",
                CategorieVehicule.UTILITAIRE, new BigDecimal("80"), StatutVehicule.DISPONIBLE, agence, new ArrayList<>(), new ArrayList<>()));

        repository.save(agence);
    }

    private void loadAgence(CrudRepository<Agence, Long> repository, String repositoryType) {
        StringBuilder sb = new StringBuilder("Depot utilise : ").append(repositoryType);
        for (Agence agence : repository.findAll()) {
            sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom());
            sb.append("\nVehicules Count : ").append(agence.getVehicules().size());
            for (Vehicule v : agence.getVehicules()) {
                sb.append("\n=== ").append(v.getIdVehicule()).append("|").append(v.getImmatriculation());
            }
        }
        fail(sb.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}

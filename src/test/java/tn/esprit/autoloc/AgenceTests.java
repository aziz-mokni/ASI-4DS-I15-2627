package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AgenceTests {
    @Autowired
    private AgenceRepositoryMock agenceRepository;

    @Test
    public void addAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        agence.getVehicules().add(new Vehicule(null, "785414TU96", "Isuzu", "DMax",
                CategorieVehicule.SUV, new BigDecimal("100"), StatutVehicule.MAINTENANCE, agence, new ArrayList<>(), new ArrayList<>()));
        agence.getVehicules().add(new Vehicule(null, "785414TU95", "Toyota", "Yaris",
                CategorieVehicule.UTILITAIRE, new BigDecimal("80"), StatutVehicule.DISPONIBLE, agence, new ArrayList<>(), new ArrayList<>()));

        agenceRepository.save(agence);
    }

    @Test
    public void loadAgence() {
        StringBuilder sb = new StringBuilder();
        for (Agence agence : agenceRepository.findAll()) {
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

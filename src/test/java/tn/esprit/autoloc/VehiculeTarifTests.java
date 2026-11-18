package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.calcul.LongueDureeOffre;
import tn.esprit.autoloc.calcul.StandardOffre;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.calcul.WeekendOffre;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.VehiculeServiceImpl;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehiculeTarifTests {

    private final VehiculeServiceImpl service = new VehiculeServiceImpl(null, Map.of(
            "STANDARD", new StandardOffre(),
            "WEEKEND", new WeekendOffre(),
            "LONGUE_DUREE", new LongueDureeOffre()), null, null);

    private double calcul(String tarif, int nbJours, VehiculeOffre offre) {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal(tarif));
        return service.calculTarif(vehicule, nbJours, offre);
    }

    @Test
    void calculTarif() {
        assertEquals(300, calcul("100", 3, VehiculeOffre.STANDARD), 0.001);
        assertEquals(240, calcul("100", 3, VehiculeOffre.WEEKEND), 0.001);
        assertEquals(1500, calcul("100", 20, VehiculeOffre.LONGUE_DUREE), 0.001);
        // Reduction de 5% uniquement au-dessus de 2000
        assertEquals(1950, calcul("100", 26, VehiculeOffre.LONGUE_DUREE), 0.001);
        assertEquals(1923.75, calcul("100", 27, VehiculeOffre.LONGUE_DUREE), 0.001);
    }
}

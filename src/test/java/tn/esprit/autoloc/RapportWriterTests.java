package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.io.RapportWriterFactory;
import tn.esprit.autoloc.io.TypeRapport;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RapportWriterTests {

    private static final String RAPPORT = "id: 1, nom: A, tel: 71\ntotal: 540.00\n";

    private final RapportWriterFactory factory = new RapportWriterFactory();

    @Test
    void ecrire() {
        assertEquals(RAPPORT, factory.creer(TypeRapport.TEXTE).ecrire(RAPPORT));
        assertEquals("total: 540.00\n", factory.creer(TypeRapport.HYBRIDE).ecrire(RAPPORT));
    }
}

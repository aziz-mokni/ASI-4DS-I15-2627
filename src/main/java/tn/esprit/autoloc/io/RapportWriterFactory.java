package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;

@Component
public class RapportWriterFactory {

    public IRapportWriter creer(TypeRapport type) {
        return switch (type) {
            case TEXTE -> new TexteRapportWriter();
            case HYBRIDE -> new HybrideRapportWriter();
        };
    }
}

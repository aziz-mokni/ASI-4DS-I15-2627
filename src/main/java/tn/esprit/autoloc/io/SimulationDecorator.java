package tn.esprit.autoloc.io;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.ISimulationService;

@Service
@Primary
public class SimulationDecorator implements ISimulationService {

    private final ISimulationService simulationService;
    private final IRapportWriter rapportWriter;

    public SimulationDecorator(@Qualifier("agenceServiceImpl") ISimulationService simulationService,
                               RapportWriterFactory rapportWriterFactory,
                               @Value("${autoloc.rapport.type}") TypeRapport type) {
        this.simulationService = simulationService;
        this.rapportWriter = rapportWriterFactory.creer(type);
    }

    @Override
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        return rapportWriter.ecrire(simulationService.simuler(agence, nbJours, offre));
    }
}

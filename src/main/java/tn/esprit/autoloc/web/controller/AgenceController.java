package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.ISimulationService;

import java.util.List;

@Tag(name = "Agences", description = "Gestion des agences de location")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;
    private final ISimulationService simulationService;

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste des agences")
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponse(responseCode = "200", description = "Agence trouvée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @GetMapping("/{id}")
    public Agence findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(summary = "Simuler le total des tarifs d'une agence", description = "Affiche l'entête sur la console et retourne le total pour un nombre de jours et une offre")
    @ApiResponse(responseCode = "200", description = "Rapport de simulation")
    @ApiResponse(responseCode = "400", description = "Nombre de jours invalide")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @GetMapping("/{id}/simulation")
    public String simuler(@Parameter(description = "Identifiant") @PathVariable Long id,
                          @Parameter(description = "Nombre de jours") @RequestParam int nbJours,
                          @Parameter(description = "Type d'offre") @RequestParam VehiculeOffre offre) {
        return simulationService.simuler(agenceService.findById(id), nbJours, offre);
    }

    @Operation(summary = "Ajouter une agence")
    @ApiResponse(responseCode = "201", description = "Agence créée")
    @ApiResponse(responseCode = "400", description = "Agence invalide ou identifiant renseigné")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "200", description = "Agence modifiée")
    @ApiResponse(responseCode = "400", description = "Agence invalide")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @PutMapping("/{id}")
    public Agence update(@Parameter(description = "Identifiant") @PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @Operation(summary = "Modifier une agence et lui ajouter de nouveaux employes", description = "Structure imbriquee : l'agence porte la liste 'employes'")
    @ApiResponse(responseCode = "200", description = "Agence modifiée, employés insérés, effectif mis à jour")
    @ApiResponse(responseCode = "400", description = "Agence ou employé invalide")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @PutMapping("/{id}/employes")
    public Agence updateAvecEmployes(@Parameter(description = "Identifiant") @PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.updateAvecEmployes(id, agence);
    }

    @Operation(summary = "Supprimer une agence", description = "Désaffecte les véhicules et employés de l'agence avant suppression")
    @ApiResponse(responseCode = "204", description = "Agence supprimée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}

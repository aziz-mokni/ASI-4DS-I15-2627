package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@Tag(name = "Vehicules", description = "Affectation / desaffectation des vehicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Consulter un vehicule")
    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Desaffecter un vehicule de son agence")
    @PutMapping("/{id}/desaffecter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desaffecterAgence(@PathVariable Long id) {
        vehiculeService.desaffecterAgence(id);
    }

    @Operation(summary = "Affecter un vehicule a une agence")
    @PutMapping("/{idVehicule}/agences/{idAgence}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void affecterAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        vehiculeService.affecterAgence(idVehicule, idAgence);
    }

    @Operation(summary = "Affecter un equipement a un vehicule")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Supprimer un vehicule")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}

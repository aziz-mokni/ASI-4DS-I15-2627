package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();
}

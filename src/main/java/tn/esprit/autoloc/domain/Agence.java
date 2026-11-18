package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "agence")
@Getter
@Setter
// Jackson passe par le constructeur vide + setters (sinon il prend @AllArgsConstructor et echoue sur l'int effectif absent)
@NoArgsConstructor(onConstructor_ = @JsonCreator)
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 50)
    private String ville;

    @Column(nullable = false, length = 255)
    private String adresse;

    @Column(length = 20)
    private String telephone;

    @JsonIgnore
    @OneToMany(mappedBy = "agence", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Vehicule> vehicules = new ArrayList<>();

    // Calcule par le service, jamais lu depuis la requete
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private int effectif;

    // Accepte en entree (structure imbriquee), jamais serialise (evite le cycle Employe.agence)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();

    // Relation symetrique : les deux cotes restent coherents en memoire
    public void addEmploye(Employe employe) {
        employes.add(employe);
        employe.setAgence(this);
    }

    public void removeEmploye(Employe employe) {
        employes.remove(employe);
        employe.setAgence(null);
    }
}

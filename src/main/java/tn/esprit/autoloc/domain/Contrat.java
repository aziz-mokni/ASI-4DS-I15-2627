package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    @Column(nullable = false)
    private LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false)
    private boolean valide;

    @OneToOne(mappedBy = "contrat")
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Paiement> paiements = new ArrayList<>();
}

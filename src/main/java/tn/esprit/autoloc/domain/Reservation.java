package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutReservation statut;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Vehicule vehicule;

    @OneToOne
    private Contrat contrat;
}

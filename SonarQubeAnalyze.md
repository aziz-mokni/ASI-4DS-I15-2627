# Analyse SonarQube — autoloc-api

Analyse : plugin **SonarQube for IDE** (IntelliJ, profil *Sonar way*) + serveur SonarQube local (`localhost:9000`, profil *Sonar way core*).

Colonne **Source** : `Serveur` = remonté par SonarQube serveur, `IDE` = règle absente de *Sonar way core*, remontée uniquement par le plugin IntelliJ.

## Types d'anomalies relevés

| Règle | Anomalie | Type | Occurrences | Source |
|-------|----------|------|-------------|--------|
| java:S4684 | Entité persistante (`@Entity`) utilisée comme `@RequestBody` | Vulnérabilité | `AgenceController.create / update / updateAvecEmployes`, `EquipementController.create` | Serveur |
| java:S2699 | Test sans assertion | Code smell (Blocker) | `AgenceTests.basicAddAgence`, `AgenceTests.fullAddAgence` | Serveur |
| java:S1186 | Méthode vide sans commentaire | Code smell | `AutolocApiApplicationTests.contextLoads` | IDE |
| java:S106 | `System.out` au lieu d'un logger | Code smell | `HybrideRapportWriter.ecrire` | IDE |
| java:S119 | Nom de paramètre de type non conforme (`ID` ≠ `^[A-Z][0-9]?$`) | Code smell | `GenericServiceImpl`, `IGenericService`, `IReadService`, `IWriteService` | IDE |
| java:S1710 | Annotation conteneur (`@ApiResponses({...})`) au lieu d'annotations répétables | Code smell | `AgenceController` (5×) | IDE |
| java:S5786 | Classe / méthode de test JUnit 5 en `public` | Code smell | `AgenceTests`, `RapportWriterTests`, `VehiculeTarifTests` | IDE |

## Impact Faible

- **java:S119** — Nommage des paramètres de type génériques.
- **java:S1710** — Annotations répétables enveloppées dans un conteneur.
- **java:S5786** — Visibilité `public` inutile des tests JUnit 5.

## Impact Moyen

- **java:S106** — Écriture sur la sortie standard au lieu d'un logger.

## Impact Important

- **java:S4684** — Entités JPA exposées directement en entrée des contrôleurs REST (POJO) : risque de *mass assignment*.
- **java:S2699** — Tests `basicAddAgence` / `fullAddAgence` sans aucune assertion (Blocker).
- **java:S1186** — Méthode vide (`contextLoads`) sans justification.

## Résolution — Impact Faible ✅

| Règle | Correction |
|-------|-----------|
| S119 | `ID` renommé en `K` dans les services génériques |
| S1710 | `@ApiResponses({ @ApiResponse(...), ... })` → `@ApiResponse(...)` répétées |
| S5786 | Suppression de `public` sur les classes et méthodes de test |

Compilation (`mvnw test-compile`) : OK.

Nouvelle analyse serveur après correction : 6 anomalies restantes (4 × S4684, 2 × S2699), toutes d'impact important. Plus aucune anomalie d'impact faible.

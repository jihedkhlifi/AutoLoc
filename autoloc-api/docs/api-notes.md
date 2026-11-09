# Atelier 5 — Notes API REST / Postman / Swagger

Projet : **AutoLoc**, package Java `tn.esprit.autolocapi`. Cette note décrit l'implémentation présente dans le dépôt fourni et les compléments de l'Atelier 5.

> **Configuration locale réelle :** `server.port=8081` (projet fourni), `server.servlet.context-path=/api`. Donc Swagger : `http://localhost:8081/api/swagger-ui.html`, OpenAPI JSON : `http://localhost:8081/api/v3/api-docs`. Le support pédagogique utilise **8080** ; adapter le port dans les requêtes ou `application.properties` si le professeur exige 8080.

## A. Endpoints

Toutes les routes ci-dessous s'appuient sur **l'interface** du service, injectée par constructeur via `@RequiredArgsConstructor`. Les contrôleurs n'utilisent jamais les repositories directement.

| Contrôleur | Verbe / route | Méthode du service | Succès | Erreurs |
|---|---|---|---|---|
| `AgenceController` | `GET /api/agences` | `IAgenceService.findAll()` | 200 | — |
| `AgenceController` | `GET /api/agences/{id}` | `IAgenceService.findById(id)` | 200 | 404 |
| `AgenceController` | `POST /api/agences` | `IAgenceService.create(entité)` | 201 | 400 |
| `AgenceController` | `PUT /api/agences/{id}` | `IAgenceService.update(id, entité)` | 200 | 400, 404 |
| `AgenceController` | `DELETE /api/agences/{id}` | `IAgenceService.deleteById(id)` | 204 | 404 |
| `EmployeController` | `GET /api/employes` | `IEmployeService.findAll()` | 200 | — |
| `EmployeController` | `GET /api/employes/{id}` | `IEmployeService.findById(id)` | 200 | 404 |
| `EmployeController` | `POST /api/employes` | `IEmployeService.create(entité)` | 201 | 400 |
| `EmployeController` | `PUT /api/employes/{id}` | `IEmployeService.update(id, entité)` | 200 | 400, 404 |
| `EmployeController` | `DELETE /api/employes/{id}` | `IEmployeService.deleteById(id)` | 204 | 404 |
| `VehiculeController` | `GET /api/vehicules` | `IVehiculeService.findAll()` | 200 | — |
| `VehiculeController` | `GET /api/vehicules/{id}` | `IVehiculeService.findById(id)` | 200 | 404 |
| `VehiculeController` | `POST /api/vehicules` | `IVehiculeService.create(entité)` | 201 | 400 |
| `VehiculeController` | `PUT /api/vehicules/{id}` | `IVehiculeService.update(id, entité)` | 200 | 400, 404 |
| `VehiculeController` | `DELETE /api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | 204 | 404 |
| `EquipementController` | `GET /api/equipements` | `IEquipementService.findAll()` | 200 | — |
| `EquipementController` | `GET /api/equipements/{id}` | `IEquipementService.findById(id)` | 200 | 404 |
| `EquipementController` | `POST /api/equipements` | `IEquipementService.create(entité)` | 201 | 400 |
| `EquipementController` | `PUT /api/equipements/{id}` | `IEquipementService.update(id, entité)` | 200 | 400, 404 |
| `EquipementController` | `DELETE /api/equipements/{id}` | `IEquipementService.deleteById(id)` | 204 | 404 |
| `ClientController` | `GET /api/clients` | `IClientService.findAll()` | 200 | — |
| `ClientController` | `GET /api/clients/{id}` | `IClientService.findById(id)` | 200 | 404 |
| `ClientController` | `POST /api/clients` | `IClientService.create(entité)` | 201 | 400 |
| `ClientController` | `PUT /api/clients/{id}` | `IClientService.update(id, entité)` | 200 | 400, 404 |
| `ClientController` | `DELETE /api/clients/{id}` | `IClientService.deleteById(id)` | 204 | 404 |
| `ReservationController` | `GET /api/reservations` | `IReservationService.findAll()` | 200 | — |
| `ReservationController` | `GET /api/reservations/{id}` | `IReservationService.findById(id)` | 200 | 404 |
| `ReservationController` | `POST /api/reservations` | `IReservationService.create(entité)` | 201 | 400 |
| `ReservationController` | `PUT /api/reservations/{id}` | `IReservationService.update(id, entité)` | 200 | 400, 404 |
| `ReservationController` | `DELETE /api/reservations/{id}` | `IReservationService.deleteById(id)` | 204 | 404 |
| `ContratController` | `GET /api/contrats` | `IContratService.findAll()` | 200 | — |
| `ContratController` | `GET /api/contrats/{id}` | `IContratService.findById(id)` | 200 | 404 |
| `ContratController` | `POST /api/contrats` | `IContratService.create(entité)` | 201 | 400 |
| `ContratController` | `PUT /api/contrats/{id}` | `IContratService.update(id, entité)` | 200 | 400, 404 |
| `ContratController` | `DELETE /api/contrats/{id}` | `IContratService.deleteById(id)` | 204 | 404 |
| `PaiementController` | `GET /api/paiements` | `IPaiementService.findAll()` | 200 | — |
| `PaiementController` | `GET /api/paiements/{id}` | `IPaiementService.findById(id)` | 200 | 404 |
| `MaintenanceController` | `GET /api/maintenances` | `IMaintenanceService.findAll()` | 200 | — |
| `MaintenanceController` | `GET /api/maintenances/{id}` | `IMaintenanceService.findById(id)` | 200 | 404 |
| `MaintenanceController` | `POST /api/maintenances` | `IMaintenanceService.create(entité)` | 201 | 400 |
| `MaintenanceController` | `PUT /api/maintenances/{id}` | `IMaintenanceService.update(id, entité)` | 200 | 400, 404 |
| `MaintenanceController` | `DELETE /api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | 204 | 404 |

- `POST /api/paiements` n'existe pas, donc doit retourner **405 Method Not Allowed**. Les paiements sont créés ou supprimés avec leur contrat.
- Les erreurs `400` listées correspondent aux validations existantes **lorsqu'une règle est violée** ou à un JSON non lisible, et les `404` proviennent de `ResourceNotFoundException` via `GlobalExceptionHandler`.
- Le schéma exact utilise des identifiants comme `idContrat`, `idAgence`, `idPaiement` (pas `id`).

## B. Annotations Jackson — associations

| Association | Annotation / côté | Justification |
|---|---|---|
| `Contrat.paiements` | `@JsonManagedReference("contrat-paiements")` | Sérialise les paiements dans le JSON du contrat ; la création permet de transmettre les enfants dans le corps parent. |
| `Paiement.contrat` | `@JsonBackReference("contrat-paiements")` | Coupe la référence inverse dans le JSON pour éviter la boucle `Contrat → Paiement → Contrat`. |
| `Agence.employes` et `Agence.vehicules` | `@JsonIgnore` | Empêche la sérialisation d'un graphe d'objets inutile ; les enfants peuvent toujours désigner l'agence par `idAgence`. |
| `Client.reservations`, `Vehicule.reservations` | `@JsonIgnore` | Évite la navigation cyclique et la multiplication des collections. |
| `Equipement.vehicules` | `@JsonIgnore` | Coupe le côté inverse de la relation plusieurs-à-plusieurs. |
| `Reservation.contrat` | `@JsonIgnore` | Coupe la boucle 1–1 avec le contrat. |
| `Reservation.client` | **Aucune `@JsonBackReference`** | Un POST de réservation doit pouvoir lire la référence du client `{ "client": { "idClient": 1 } }`. |

**Différence :** `@JsonBackReference` marque le côté enfant d'une paire cohérente `@JsonManagedReference/@JsonBackReference` et permet la liaison parent-enfant. `@JsonIgnore` exclut simplement un champ du JSON (lecture/écriture selon placement et configuration) sans gérer automatiquement une paire parent-enfant. Dans `ContratServiceImpl.create()`, le côté propriétaire JPA de chacun des paiements est explicitement lié au contrat avant la sauvegarde pour garantir la cascade.

**Limite connue :** les entités JPA sont exposées directement, uniquement pour cet atelier. Avec de vraies données, les relations LAZY peuvent nécessiter un traitement supplémentaire ; des DTO seront introduits au chapitre 10.

## C. Postman et Swagger

**Postman :** importe `postman/AutoLoc-Atelier5.postman_collection.json` et `postman/AutoLoc-local.postman_environment.json`. Choisir l'environnement **local**. Les dossiers regroupent des requêtes normales et des cas d'erreur, chacune avec des assertions de statut. L'ID obtenu au POST est sauvegardé en variable, p. ex. `contratsId`.

**Scénario cascade Contrat / Paiement :**

1. Exécuter `Contrats > 07 - Creer contrat + 2 paiements (cascade)` ; résultat attendu **201**, deux paiements dans le contrat.
2. Exécuter `Paiements (lecture seule) > Lister les paiements` ; résultat attendu **200**, les paiements apparaissent en base.
3. Exécuter `Contrats > 08 - Supprimer contrat de cascade` ; résultat attendu **204**.
4. Exécuter `Contrats > 09 - Paiement supprime par cascade` ; résultat attendu **404**.
5. Exécuter `Paiements (lecture seule) > Creer un paiement (refuse 405)` ; résultat attendu **405**.

Ces statuts sont **attendus**, pas des preuves de tests exécutés : il faut démarrer l'application avec MySQL et cliquer sur **Send** dans Postman.

**Swagger UI :** disponible sous `/api/swagger-ui.html` sur le port configuré ; fournit une documentation synchronisée avec le code, les 9 groupes `@Tag`, des résumés `@Operation` et des réponses `@ApiResponses`. Vérifier les endpoints `GET /api/contrats`, `POST /api/contrats` et `GET /api/contrats/{id}` depuis `Try it out > Execute` ; le descriptif machine est disponible à `/api/v3/api-docs`.

Postman est idéal pour rejouer les tests et contrôler les statuts ; Swagger décrit et explore les endpoints publiés.

## D. Analyse SonarQube for IDE — compte rendu honnête

**À faire dans IntelliJ :** clic droit sur `web.controller` → **Analyze with SonarQube for IDE**. Le plugin SonarQube for IDE n'a pas été exécuté ici : aucun numéro de règle ni résultat de scan n'est inventé. Voici deux points de revue manuelle traités lors de l'atelier, à rapprocher des résultats Sonar :

| Point de revue manuelle | Risque | Correction apportée | Vérification |
|---|---|---|---|
| Exception sur corps JSON mal formé ou sur valeur de type invalide | Réponse d'erreur non homogène dans les essais Postman | Ajout d'un `@ExceptionHandler(HttpMessageNotReadableException.class)` qui retourne un JSON de statut **400** | Envoyer un JSON mal formé, contrôler `statut`, `message`, `horodatage`. |
| Création `Contrat` avec paiements non reliés par le côté propriétaire `Paiement.contrat` | Échec de clé étrangère non nulle et cascade incohérente | Affectation explicite `paiement.setContrat(contrat)` avant `save()` | Tester `POST /contrats` avec 2 paiements et les consulter ensuite. |

**Point de conception à expliquer (non corrigé volontairement)** : les contrôleurs exposent les entités JPA, ce qui couple l'API à la base et peut exposer des données internes. L'Atelier 5 demande ce choix provisoire ; les DTO seront ajoutés au chapitre 10.

## E. Questions de compréhension

**1. Qui reçoit les requêtes ?** `DispatcherServlet` est le point d'entrée Spring MVC. Il utilise les mappings HTTP (`@RequestMapping`, `@GetMapping`, etc.) via les mécanismes de `HandlerMapping` pour choisir le contrôleur et la méthode, puis Jackson sérialise l'objet de réponse en JSON.

**2. Pourquoi l'interface `IContratService` ?** Pour respecter les couches et l'inversion de dépendance : le contrôleur décrit le besoin métier, sans connaître l'accès aux données (`Repository`) ni dépendre d'une implémentation concrète (`ContratServiceImpl`). L'implémentation est injectée par constructeur.

**3. Pourquoi 201 / 204 / 404 au lieu de 500 ?** `201 Created` indique une ressource créée par POST. `204 No Content` indique une suppression réussie sans corps HTTP. `404 Not Found` signifie que l'identifiant désigne une ressource absente et c'est une situation attendue ; `500` est réservé à une erreur interne inattendue.

**4. Pourquoi seulement GET pour les paiements ?** `PaiementService` est volontairement en lecture seule ; les paiements suivent le cycle de vie d'un contrat et la cascade JPA. Un `POST /api/paiements` reçoit **405 Method Not Allowed**.

**5. Risques JPA, `@JsonManagedReference` vs `@JsonIgnore` ?** Exposer les entités peut révéler des champs internes, engendrer une sérialisation récursive et créer des problèmes de chargement LAZY ; cela couple aussi les clients au modèle. `Contrat.paiements` utilise `@JsonManagedReference` car les paiements font partie du JSON de création du contrat ; `Agence.vehicules` utilise `@JsonIgnore` car on ne crée pas ses véhicules en cascade via le JSON de l'agence.

## Vérification et remise

- [x] 9 contrôleurs en place, 8 CRUD et paiements GET seulement
- [x] `@Tag`, `@Operation`, `@ApiResponses`, `@Parameter`
- [x] `springdoc-openapi-starter-webmvc-ui` et classe `OpenApiConfig`
- [x] Paire Jackson Contrat ↔ Paiement déjà présente ; liens propriétaire explicités
- [x] Collection Postman et environnement importables avec cas normaux et erreurs
- [x] Note demandée (sections A à E)
- [ ] Démarrer MySQL et l'application et **exécuter réellement** la collection Postman
- [ ] Vérifier Swagger UI via navigateur
- [ ] Lancer réellement SonarQube for IDE, relever ses deux résultats authentiques et mettre à jour la section D
- [ ] `mvn clean verify` et `git commit` / `git push` sur le poste de travail

**Remarque :** le modèle fourni n'a ni champ `quantite` pour Equipement ni `cout` pour Maintenance ; les cas 400 testent donc le libellé vide et une date de fin antérieure, respectivement, plutôt que des champs inexistants.

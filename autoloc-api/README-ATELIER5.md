# Démarrage rapide — Atelier 5 AutoLoc

1. Ouvrir le sous-projet `autoloc-api` (qui contient `pom.xml`) dans IntelliJ IDEA.
2. Lancer MySQL. La configuration d'origine utilise `jdbc:mysql://localhost:3306/autoloc_db?createDatabaseIfNotExist=true`, utilisateur `root`, mot de passe vide : adapter ces paramètres à votre installation locale.
3. Importer / recharger les dépendances Maven ; compiler et lancer `AutolocApiApplication`.
4. Ouvrir Swagger : **http://localhost:8081/api/swagger-ui.html** ; OpenAPI JSON : **http://localhost:8081/api/v3/api-docs**.
5. Dans Postman, importer les deux fichiers de `postman/`, sélectionner l'environnement `local`, puis exécuter les requêtes d'un dossier dans l'ordre (POST avant les GET / PUT / DELETE par ID).
6. Consulter `docs/api-notes.md` pour toutes les routes, le scénario cascade Contrat/Paiement, les questions et les vérifications manuelles restantes.

**Attention :** le port fourni est `8081` et non `8080` comme dans le support. Les entités utilisent des identifiants `idAgence`, `idClient`, `idContrat`, etc. Il n'est pas nécessaire de renommer ces champs.

**État des tests :** la compilation Maven, les requêtes Postman, Swagger UI et l'analyse SonarQube ne peuvent pas être certifiées par les modifications de fichiers seules ; il faut les effectuer dans l'environnement de développement avec une base MySQL fonctionnelle.

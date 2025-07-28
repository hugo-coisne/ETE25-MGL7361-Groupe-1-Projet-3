# Projet 3 - MGL7361 - architecture logicielle

**Objectifs** : 

- Transformer une application à architecture monolithe modulaire en architecture basée sur des microservices en utilisant la chorégraphie pour gérer les flux de travail (workflows)

- Construire une architecture physique et utiliser un serveur de bases de données SQL, dans lequel chaque microservice aura son propre schéma de données que lui seul pourra y accéder et mettre à jour (pas de jointure de tables en dehors des données du microservice)

- Implémenter les différents microservices selon l'architecture en couches où l'interface utilisateur correspondra à une API REST implémentant les fonctionnalités exposées aux autres microservices

- Documenter les APIs REST avec [Swagger](https://swagger.io/tools/open-source/), selon la spécification [OpenAPI](https://www.openapis.org/)

- Préparer un jeu de tests, dans le programme principal, pour simuler le fonctionnement de l'application à l'aide d'appels aux différentes APIs REST des différents microservices

## Utilisation

### Prérequis
- Maven pour le build
- Docker et Docker Compose pour le déploiement

### Comment lancer l'application

- Cloner le projet et l'ouvrir
- Exécuter la commande suivante pour le démarrer :
```bash
docker compose up db -d && ./build-all.sh && docker compose build && docker compose up -d
```

### Pour voir l'exécution du programme principal (`Main.java`) :
```bash
docker compose logs main
```

## Troubleshooting

- Il peut arriver, notamment lorsque l'on démarre pas le projet pour la première fois, que, par exemple, la création de compte, au début de l'éxécution du Main, échoue. Cela est dû au fait que lors de l'exécution du Main, on commence par créer un utilisateur et, lorsque l'on redémarre le projet, le Main est exécuté à nouveau mais la création de compte échoue car le compte est déjà enregistrer dans la base de données à cause de l'éxécution précédente. Ainsi, pour résoudre ce problème, il faut arrêter la base de données avec `docker compose down db`, supprimer le répertoire de persistence du conteneur mariadb `db_data` qui a dû être créé à la racine du projet puis relancer la base de données avec `docker compose up db -d`.

- Il peut arriver que le conteneur MariaDB mette longtemps à démarrer et, ce faisant, entraine une erreur lorsqu'un service tente de parler avec la base de données. L'instruction de démarrage du projet est déjà pensée pour éviter un maximum ce problème. Cependant, cela peut toujours se produire. Dans ce cas, faire `docker compose down` pour tout arrêter, supprimer la base de données (voir plus haut), faire `docker compose up db` puis attendre que mariadb ait exposé son port (3306) en consultant ses logs via `docker compose logs db`, puis faire `docker compose up -d` pour démarrer le reste des services.

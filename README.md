# Projet 3 - MGL7361 - architecture logicielle

**Objectif** : Transformer une application à architecture monolithe modulaire en architecture basée sur des microservices 

## Comment lancer l'application

- Cloner le projet puis :

```bash
docker compose build && docker compose up -d
```

- Pour voir l'exécution du "Main" :
```bash
docker compose logs main
```

## Troubleshooting

- Si il y a des problèmes au niveau des données, vous pouvez supprimer la base de données et la relancer en lançant le `init.sql` à la racine du projet.
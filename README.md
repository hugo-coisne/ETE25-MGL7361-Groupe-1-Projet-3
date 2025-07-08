# Projet 2 - MGL7361 - architecture logicielle

## Objectif

- Construire une architecture logique selon le style architectural monolithe
  modulaire
- Construire une architecture physique et utiliser un serveur de bases de
  données SQL
- Implémenter les différents modules selon l'architecture en couches
- Implémenter les APIs (Interfaces) publiques de chaque module.

## Comment lancer l'application

- Cloner le projet
- Lancer la base de données avec Docker :

```bash
docker compose up -d
```

- Lancer l'application avec Maven. Le main se trouve dans `src/main/src/main/java/Main.java`.

## Troubleshooting

- Si il y a des problèmes au niveau des données, vous pouvez supprimer la base de données et la relancer en lançant le `init.sql` à la racine du projet.
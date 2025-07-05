# 📦 Module `delivery` — Gestion des livraisons

Ce module fait partie d'une application de gestion de commandes et gère toute la logique liée aux **livraisons**.

---

## 📐 Architecture

Le module suit une **architecture en couches** bien structurée :

- **Présentation (`DeliveryAPI`)** : API.
- **Business (`DeliveryService`)** : logique métier.
- **Modèle (`Delivery`, `Address`)** : entités principales.
- **DAO (`DeliveryDAO`, `AddressDAO`)** : accès aux données en base de données.
- **DTO (`DeliveryDTO`, `AddressDTO`)** : objets de transfert de données pour communication entre couches.
- **Mapper** : conversion entre `Model` et `DTO` (ex. `AddressMapper.toDTO()`).
    
---

## ✅ Tests

Les tests sont organisés par couche :

### 🔸 Couche *persistence*

- Testée via `DeliveryDAOTest` et `AddressDAOTest`
- ⚠️ Les tests s’exécutent **directement sur la base de données**, et **réinitialisent automatiquement le schéma** via le fichier `init.sql`.
  - ➕ Avantage : pas besoin de lancer manuellement `init.sql`
  - ⚠️ Inconvénient : **les données sont perdues à chaque test** car un `DROP TABLE IF EXISTS` est effectué

### 🔸 Couche *business*

- Testée via `DeliveryServiceTest`
- Vérifie la logique métier sans s'occuper de la présentation

### 🔸 Couche *présentation*

- Non testée par JUnit : les scénarios sont déclenchés **dans le `main()`**, notamment via la méthode `delivery()`

---

## 🚀 Exécution de l’application

La méthode principale à exécuter est :

`Main.delivery();`

Elle illustre les **étapes 8 à 10** des spécifications appliquées à `delivery`, à savoir :

8. Le système se charge de la livraison de la commande aux dates de livraison prévues
   pour chaque livre
9. Voir la liste des commandes en attente de livraison ainsi que l'historique des
   commandes livrées
10. Une fois un livre est livré, sa date de livraison dans la commande est mise à jour et
    son status passe de "En attente de livraison" à "Livré"

---

### ℹ️ Remarques importantes

- Le statut du delivery est **modifié de façon irréversible** dans `Main.delivery()`
  - Il n’est **pas réinitialisé automatiquement**
  - Pour revenir à l’état initial :
    1. Réexécuter `init.sql`
    2. Relancer le programme `Main`

- Le `orderNumber` utilisé pour tester dans le `Main` est **hardcodé** :  
  ```
  orderNumber = "20250623-AAAABBBB"
  ```
  Ce numéro est **préexistant** dans la base, injecté via `init.sql`.

---

## ✍️ À faire plus tard (améliorations possibles)

- Compléter le `TODO` de récupération des `items` dans les commandes (`OrderDTO`)
- Implémenter un logger
- Gérer les exceptions
# 🎮 Projet Go - Moteur de Jeu Stratégique

Ce projet consiste en l'implémentation d'un moteur de jeu en Go, conçu pour être flexible et compatible avec différents types de jeux de plateau. L'accent a été mis sur la qualité du code et le respect des principes de conception logicielle.

## 🛠 Concepts Clés & Design
Le cœur du projet repose sur le respect des **principes SOLID** pour garantir une structure évolutive et maintenable :
* **Responsabilité Unique (SRP)** : Organisation structurée du code pour séparer la logique du plateau, des joueurs et des moteurs de décision.
* **Open/Closed Principle** : Application flexible et fonctionnelle permettant l'intégration de différents jeux sans modifier le noyau du moteur.
* **Liskov Substitution** : Gestion de trois types de joueurs interchangeables (Humain, BotNaif, et BotMiniMax).

## 🚀 Fonctionnalités Opérationnelles
* **Types de Joueurs** :
    * **Humain** : Interface de saisie directe.
    * **BotNaif** : Coups aléatoires ou basiques.
    * **BotMiniMax** : Implémentation d'une IA basée sur l'algorithme MiniMax pour la prise de décision stratégique.
* **Moteur de Jeu** :
    * Commandes supportées : `play`, `setBoardSize`, `clearBoard`, `genMove`.
    * Gestion dynamique de la taille du plateau.
    * Détection automatique des conditions de victoire.
* **Algorithme `genMove`** : Une implémentation astucieuse pour le calcul des déplacements.

## 🧪 Tests & Stabilité
La robustesse de l'application est garantie par une série de **11 tests unitaires** couvrant les aspects critiques :
* Initialisation et manipulation du plateau (lecture/écriture des cases).
* Validation de la méthode `genmove`.
* Vérification de toutes les possibilités de victoire (horizontale, verticale, diagonale) pour les deux joueurs.
**Résultat** : 100% des tests passent avec succès.

## 📊 Architecture
Le projet utilise une structure modulaire permettant une séparation claire entre les données et l'intelligence artificielle.
*Note : Bien que fonctionnel, l'algorithme MiniMax reste dans une version initiale et l'instanciation des joueurs gagne à être déléguée à la classe App dans les prochaines versions pour parfaire l'architecture.*

## ⚠️ Limitations Actuelles
* La méthode `setPlayer` (définition dynamique du type de joueur) n'est pas encore opérationnelle.
* L'instanciation des joueurs est actuellement gérée directement par la classe `Board`.

---
**Contributeurs (GR 206)** : Lukas STAVROPOULOS, Rayen BOUZIDI, Leo LIN, Mathias GILLET.
*Projet réalisé dans le cadre du BUT Informatique.*

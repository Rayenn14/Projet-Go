206 STAVROPOULOS Lukas BOUZIDI Rayen LIN Leo GILLET Mathias
# Projet-Go

Les fonctionnalités qui marchent :
- Les trois types de joueurs : Humain, BotNaif, et BotMiniMax (profondeur?)
- play, setBoardSize, clearBoard, genMove
- Application flexible et fonctionnel avec différents jeux
- Condition de victoire


Les fonctionnalités qui ne marchent pas :
- setPlayer (définir si le joueur est humain ou un robot)

Diagramme d'architecture
![image](https://github.com/user-attachments/assets/1b6d930f-cc35-42cd-b9b1-9b1c11a67294)

Nombre total de tests : 11 tests unitaires.
Ce que nous avons testé :
- Initialisation du plateau
- La methode genmove
- Toutes les possibilités de victoires des deux joueurs et dans tous les sens 
- Les fonctionnalités de base du plateau de jeu (lecture/écriture des cases).
Résultat : Tous les tests passent, démontrant que les fonctionnalités principales sont stables et fonctionnelles.

BILAN : 
L'implémentation actuelle de l'algorithme miniMax reste encore très superficielle. De plus, la classe Board instancie directement les joueurs, ce qui pourrait être amélioré en déléguant cette responsabilité à la classe App, que nous n'avions pas réussi à faire. Enfin, la méthode setPlayer ne fonctionne pas. En dépit de cela, nous sommes très satisfait du travail accompli. La méthode genmove est assez astucieuse et l'implémentation des conditions de victoire a été réussie malgré la difficulté surprenante pour les mettre en place. Nous avons également veillé à organiser notre code de manière structurée, en respectant le plus possible les principes SOLID. 

# l2s4-projet-2023

# Equipe

- Hely Angel
- Signouret Nathan
- Bou Alexandre
- Potel Nicolas


# Sujet

[Le sujet 2023](https://www.fil.univ-lille.fr/~varre/portail/l2s4-projet/sujet2023.pdf)

# UML 

[Diagramme UML (Lucid)](https://lucid.app/lucidchart/ae48739f-e2cc-4d42-912b-a957caebbfc9/edit?viewport_loc=224%2C100%2C1487%2C664%2CmnHr0YKO_jvM&invitationId=inv_9854fa08-91ab-4241-9a48-a2df535e1ce3)

# Livrables

## Livrable 1

Cette section constitue les informations portant sur le premier livrable du projet.
Voici les commandes pour compiler et éxécuter le projet (se placer dans le dossier principal, celui qui contient les dossiers: src, test,...):
- ``` mkdir classes ``` <- Pour créer le dossier de destination des fichiers de compilation
- ``` javac -classpath jars/json-java.jar:src -d classes src/main/JsonMain.java ``` <- Pour compiler
- ``` java -cp jars/json-java.jar:classes main.JsonMain 12Cities.json ``` <- Pour la version avec 12 villes
- ``` java -cp jars/json-java.jar:classes main.JsonMain 48Cities.json ``` <- Pour la version avec 48 villes

### Atteinte des objectifs

La représentation des différentes villes, et de la carte de manière générale est correcte. De plus, nous avons créé des classes gérant le fichier source JSON de manière à générer automatiquement la map selon celui-ci. Ensuite, nous avons également implémenté l'algorithme de propagation, même si il est possible que des modifications soient à apporter dans le futur du projet.

Notre méthode qui permet de gérer l'infection est la méthode addCubes qui peut être décrite de la manière suivante : 

```
Si la ville n'est pas infectée
       Si le nombre de cubes de la ville ne va pas dépasser la limite après l'attribution des cubes
              Le nombre de cubes de la ville augmente de n cubes
       Fin Si
       Sinon
              Le nombre de cubes de la ville passe à 3
              Pour toute les villes c des voisins de la ville en question
                     On augmente de 1 leur nombre de cubes en rappelant cette fonction
              Fin Pour
       Fin Sinon
       La ville est donc infectée
       Fin Si
}
```

### Difficultés restant à résoudre

Implémentation des différents types de cartes, du système de jeu et mise en place des rôles.

## Livrable 2

Voici la commande pour compiler,générer la documentation et éxécuter le projet (se placer dans le dossier principal, celui qui contient les dossiers: src, test,...):<br/>
(On supposera que le dossier principal contient un dossier jars qui contient le jar json-java.jar qui permet l'utilisation de la bibliothèque json en java)
- ``` make delivery2 ``` 

Après avoir commencé l'éxécution du programme main, suivre les instructions affichées pour effectuer les actions suivantes:
- initialisation du plateau de jeu, des cartes et de chacun des joueurs avec un rôle aléatoire et un nom choisi par l'utilisateur.
- tirage de 2 cartes infection et les jouer.
- tirer 2 cartes joueur pour chaque joueur et les ajouter à sa main, si une carte est une carte épidémie, le programme joue l'action de celle-ci.

### Atteinte des objectifs

Implémentation des rôles et des différents types de cartes effective.
La méthode play progresse correctement: la map, les joueurs et les deck sont tous implémentés.

### Difficultés restant à résoudre

implémenter un moyen d'afficher les actions possibles pour un joueur.

## Livrable 3

### Atteinte des objectifs

### Difficultés restant à résoudre

## Livrable 4

### Atteinte des objectifs

### Difficultés restant à résoudre

# Journal de bord

## Semaine 1
Vendredi 27/01 : Mise en place des premiers éléments du diagramme UML, notamment pour la modélisation de la carte.
Création des diagrammes UML des packages map, pandemic, player et des classes Sickness et Game.
A faire: terminer le diagramme UML, répartition des tâches à effectuer, commencer le code des classes du package Map pour le premier rendu.

## Semaine 2
Vendredi 3/02 : Mise en place des premiers éléments de codage, création des packages map,sickness et player. Les classes City, JsonMap, WorldMap, Player, Sickness et les fichiers json ont été mis en place.
A faire: les tests des classes définies, avancer sur le diagramme UML.

## Semaine 3

Mardi 07/02 : mise en place de la librairie Junit4, création des accesseurs pour player, et mise en place du package card ainsi que l'interface Card.
A faire: les tests des classes définies, avancer sur le diagramme UML ainsi que la méthode  JsonNeighbours pour créer les voisins.

Dimanche 12/02 : 
 - Création des méthodes move(c : City) et ToString() dans player.Player.
 - création des tests pour les accesseurs et la méthode move().
 - Création des méthodes isNeighbour(c : City) et hasPlayer(p : Player) dans map.City pour faciliter la création de la méthode move() ainsi que ses tests. 
 - ajout de tests pour les méthodes isNeighbour() et hasPlayer().

## Semaine 4
Vendredi 17/02 :
 - Modifications du readme pour le premier livrable
 - Mises à jour de l'UML et modifications du code en conséquence

## Semaine 5
Jeudi 23/02 : 
- ajout de l'attribut game dans player 
- création d'une méthode buildStation dans player
- création d'une méthode AddStationInGame dans game pour la méthode buildStation 
- ajout du rôle expert dans la package rôle 
- modification dans certaines classes par rapport à des modification de nom ou d'ajout d'attribut 

## Semaine 6

Jeudi 02/03 :
- création de la méthode action pour InfectionCard
- ajout des fonctions acceleration, infection, intensification et action de la classe PandemicCard
- ajout d'un attribut cures : Map<Sickness, Boolean> pour définir les maladies guérie ou non. 
- ajout de deux méthodes dans game : isSafe(s : sickness) pour savoir si une maladie est guérie ou non et setCure(s : sickness) pour définir qu'une maladie est guérie.
- ajout de deux méthodes dans player : hasFiveIdenticCards(s : Sickness) pour savoir si un joueur possède 5 cartes de la même maladie et de la méthode findCure pour guérir une maladie.
- ajout de la classe Scientific dans le package Player.role

faire les test pour les nouvelles méthodes et voir si il est possible d'optimiser les deux nouvelles méthodes et si elles ont besoins de modifications.   

## Semaine 7

- Mise en place de la classe Deck qui gère la pile et défausse d'une partie.
- Avancée dans les tests de game.
- Ajout des méthodes:
	- getResult() qui renvoie l'avancée de la partie.
	- getRandomNumber() qui renvoie un nombre aléatoire.
	- createPlayer() qui crée les objets correspondant aux joueurs.

## Semaine 8

Mardi 14/03 :
- ajout de plusieurs méthodes dans game :
		- getStationList()
		- removeStation() 
		- CreateStation()
		- DisplayStation()
		- addPlayer(p : Player)
		- getPlayerList()
		- getActifPlayer()
		- NextPlayer()
- ajout /finition de méthodes : 
		- ChoosStation()
		- BuildStation()
- modification de méthodes dans expert 
- modification et ajout de tests dans GameTest
- A faire : 
		- faire les tests dans player 
		- finir les tests et le construteur dans game

## Semaine 9

- Mise en place des différentes méthode d'initialisation d'une partie: 
		- initMap()
		- initPlayer() 
		- initDeck()
- Finalisation des méthodes de la classe Deck
- Finalisation de la mise en place des rôles
- Ajout de la documentation dans le Makefile
- A faire:
		- poursuivre l'avancée de play()
		- gérer l'affichage des données au fur et à mesure de la partie

## Semaine 10

## Semaine 11

## Semaine 12

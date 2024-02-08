Projet Dactylo-Game : binome 76
-

Hou Maxime 21955186 groupe 5

Comment compiler ?
-
- se placer à l'emplacement du Makefile
- make : pour compiler le tout
- make run : pour lancer le programme
- make clean : pour nettoyer tous les .class

Comment fonctionne le jeu ?
-
Le jeu possède 2 modes en solo : "normal" et "jeu".

Il y a des boutons à l'écran compréhensible d'eux même (start, retour etc..).

Le mode normal : 
- Vous pouvez choisir parmis 3 temps : 30s, 60s et 120s. 
- Le but est d'écrire le plus de mot correctement dans le temps imparti, un mot est validé en appuyant sur espace après avoir écrit toutes les lettres.
- Si toutes les lettres ne sont pas écrites, vous ne pouvez pas valider le mot.
- S'il y a une erreur, vous ne pouvez pas l'effacer.
- A chaque mot validé, il est retiré de la file de 15 mots et un nouveau mot est ajouté à la fin.

Le mode jeu :
- Vous commencez avec 10 vies et au niveau 0.
- Le niveau augmente tous les 5 mots correctement tapés (au lieu de 100 comme demandé, car c'est trop long je trouve).
- Au début il y a 10 mots à l'écran, dont 1 mot bonus qui redonne des vies proportionnellement  à la taille du mot.
- à chaque x secondes donné par une fonction f(n) = 3*(0.9^n) où n est le niveau, un mot apparait avec 20% de chance d'être un bonus.
- La partie termine si vos vies sont à 0.
- S'il y a 15 mots à l'écran, le premier mot est automatiquement retiré peu importe si il est valide ou non (/!\ possibilité de perdre des vies), et un nouveau mot est ajouté à la fin.


Le mode multijoueur n'a pas été implémenté (même si le bouton est présent) car je n'ai pas eu le temps.
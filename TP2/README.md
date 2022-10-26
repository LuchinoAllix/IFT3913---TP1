# IFT 3913 : TP2

Université de Montréal - IFT3913 Qualité de Logiciel et métriques – Automne 2022 – Tavaux pratiques

Membres :

- Anthony GRANGE 20160453
- Luchino ALLIX-LASTREGO 20222844

<br/>

# Comment lancer le programme :

Deux options :	

1) Dans l'invite de commande, écrire : 

```
java -jar path_to_TP2.jar folder_to_analyse

```

2) Se déplacer dans l'invite de commande à l'endroit du fichier `TP2.jar` et écrire :

```
java -jar TP2.jar folder_to_analyse
```

Remarques : 

- Lors de la création du `TP2.jar` nous avons utiliser la version 19 de java, elle est donc recomandée pour pouvoir exécuter le programme.
- Un fihier `output.csv` sera crée (ou remplacé si déjà existant) au même emplacement que le fichier `TP2.jar`.
- Si aucun dossier à analyser n'est entré en ligne de commande le message suivant sera affiché : 

```
Error : 1 argument is needed
Please try again with a folder path
```

- Une erreur courrante qui empêche le programe de fonctionner correctement est le fait que le fichier `output.csv` soit ouvert, il faut donc qu'il soit fermé.

<br/>

# Imformations supplémentaires

Dans ce repository vous trouverez :

- L'énoncé du TP2
- Un `output.csv` déjà généré sur la librairie `jfreechart`.
- Le dossier métrique qui comporte tout le code utilisé dans le TP brièvement :
	- Chaque métrique possède sa classe java, sauf `PMNT.java` qui s'occupe de PMNT et TPC et `DC` qui s'occupe de DC, LOC et CLOC
	- il y également des classes `ASTparser....java` qui permettent de parcourir le code
	- une classe `MyMethod.java` qui facilite le calcule de PMNT et TPC 
	- et bien évidemment `main.java` d'où se lance le programme
- `Notes.txt` qui a aidé aux choix des métriques
- `todo.txt` pour l'organisation des tâches
- `RapportTP2.pdf` le rapport (et `RapportTP2.docx` sont équivalent word)
- `README.txt` l'équivalent de `README.md` pour la remise.

TODO maven javaparser truc package names blabla

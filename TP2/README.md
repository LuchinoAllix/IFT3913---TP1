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
- Un fichier `output.csv` que nous avons généré sur la librairie `jfreechart`.
- Le dossier métrique qui comporte tout le code utilisé dans le TP brièvement :
	- Chaque métrique possède sa classe java, sauf `PMNT.java` qui s'occupe de PMNT et TPC et `DC` qui s'occupe de DC, LOC et CLOC
	- il y également des classes `ASTparser....java` qui permettent de parcourir le code
	- une classe `MyMethod.java` qui facilite le calcule de PMNT et TPC
	- et bien évidemment `main.java` d'où se lance le programme
- `Notes.txt` qui a aidé aux choix des métriques
- `todo.txt` pour l'organisation des tâches
- `RapportTP2.pdf` le rapport (et `RapportTP2.docx` sont équivalent word)
- `README.txt` l'équivalent de `README.md` pour la remise.

Nous utilisons la librairie `Javaparser` pour parse le code à analyser. C'est une librairie gratuite qui permet de transformer du code Java en arbre AST afin de l'analyser. (lien : https://github.com/javaparser/javaparser, documentation : https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/index.html).

Pour simplifier l'utilisation de JavaParser dans IntellijIdea nous avons créée le projet en `Maven` qui permet d'importer très facilement la librairie.

Note : Nous avons créé le repo github avant le projet Maven, il ne contient que les fichiers pertinents et non le projet Maven entier. Ainsi les fichiers de métriques, de parsing, et le fichier main se trouvent dans le package metriques sur github mais sont en réalité dans le package org.example dans notre projet Maven. Pour executer le code dans un IDE en projet Maven il ne faut pas oublier d'ajouter la dépendance Javaparser au fichier : pom.xml
````    
<dependencies>
  <dependency>
    <groupId>com.github.javaparser</groupId>
    <artifactId>javaparser-core</artifactId>
    <version>3.24.4</version>
  </dependency>
</dependencies>
````

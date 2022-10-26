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



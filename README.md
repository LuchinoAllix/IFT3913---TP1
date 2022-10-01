# IFT3913---TP1

Université de Montréal


IFT3913 QUALITÉ DE LOGICIEL ET MÉTRIQUES – AUTOMNE 2022 – TRAVAIL PRATIQUE 1


Auteurs :

- Anthony GRANGE 2016453
- Luchino ALLIX-LASTREGO 20222844


Documentation :

	Pour lancer les fichiers '.jar' il faut : taper 'java -jar nomDuFichier.jar' suivi des arguments.

    Pour exécuter les 4 programmes il faut passer leurs arguments respectifs.

    Execution Partie 0 - Lancer l'executable IFT3913---TP1-P0.jar avec le path du repertoire en argument
        Le fichier output.csv sera créé contenant le résultat de jls

    Execution Partie 1 - Lancer l'executable IFT3913---TP1-P1.jar avec le path du fichier en argument
        Le resultat seras affiché dans la console

    Execution Partie 2 - Lancer l'executable IFT3913---TP1-P2.jar avec le path du fichier csv en argument
        Le fichier output2.csv sera créé contant le résultat du fichier csv initial augmenté
        par le résultat de lcsec

    Execution Partie 3 - Lancer l'executable IFT3913---TP1-P3.jar avec le path du repertoire et un seuil (int) en argument
        Le fichier output_Seuil_x.csv ou x est le seuil passé en argument sera créé contenant le résultat de egon

    Execution Partie 4 - Lancer l'executable IFT3913---TP1-P4.jar avec le path du repertoire en argument
        Les fichiers output_Seuil_1.csv, output_Seuil_5.csv, output_Seuil_10.csv seront créés contenant les résultats
        de egon avec des seuils de 1%, 5% et 10% respectivement

Informations supplémentaires :

Toutes les informations sur les choix qui ont pu être pris durant la conception des différentes méthodes sont spécifiés dans
la documentation des différentes méthode.

Dans la partie 2 :
lcsec ne modifie pas le fichier csv original. Création d'un nouveau fichier csv augmenté par les résultats de lcsec
D'après une réponse de l'instructeur sur piazza nous avons considéré uniquement les mentions de noms d'autres classes
pour la métrique de csec (Voir Q@36 sur piazza).
lcsec ne prends pas de String path en argument car tous les paths nécéssaires sont dans le fichier csv

Dans la partie 3 :
Nous avons décidez de minorer le seuil, c'est à dire que si on regarde 10 classes mais que l'on a un seuil de 1% on ne
regarde aucune classe. En effet, on estime que si on veut 1% des classes suspectes alors il faudrait au moins 100 classes.

Nous avons utilisé la version 18.0.1.1 de java pour la création des fichiers jar. Au moins cette version est donc nécessaire pour exécuter les .jar .

Pour le même code nous avons trouvé des résultats légèrement différents entre Windows et MacOs que nous n'avons pas
réussi à expliquer ou régler. La différence étant de 1 résultat en plus dans les fichiers csv output_Seuil_5.csv et
output_Seuil_10.csv sur MacOs.

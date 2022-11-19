# IFT 3913 : TP3

Université de Montréal - IFT3913 Qualité de Logiciel et métriques – Automne 2022 – Tavaux pratiques

Membres :

- Anthony GRANGE 20160453
- Luchino ALLIX-LASTREGO 20222844

Lien du repository : https://github.com/LuchinoAllix/IFT3913---TP1

(Ceci est le liens pour tout les TPs mais il a été fait pour le TP1 (d'où le nom), pour ce TP il faut donc aller dans le dossier TP3)

# Comment lancer le programme :

Vu que ce TP ne demande pas de livrable, nous avons prit la liberté d'effectuer le TP en python.
Il n'y qu'un seul fichier `main.py`. Il faut avoir `python 3.10` pour le lancer ainsi que les modules suivants :
- ``matplotlib``
- ``numpy``
- ``scipy``
- ``csv``

Il faut également avoir un fichier à analyser comme `jfreechart-stats.csv`.
(Ce fichier est également disponible dans le repository).

Le programme génère 9 graphiques, ainsi que tu text dans l'invite de commande.
Ces graphiques sont disponibles au format `png` dans le dossier `plots`. Le text à lui été copié dans `output.txt`.
Pour ne pas générer ces graphiques il suffit de retirer (où mettre en commentaire) la ligne 187 :
```
plt.show()
```

NB : Veillez à ce que le fichier `jfreechart-stats.csv` ne soit pas ouvert pour que le progamme fonctionne correctement.
Techniquement il peut être ouvert mais le modifier pendant la lecture poserait problème.

# Information supplémentaire :

Dans ce repository vous trouverez :

- `.idea` et `__pycache__` que vous pouvez ignorer (pour la config python & pycharm).
- `plots` qui comme mentionné précédement possède les graphiques généré.
- `README.md` ce que vous êtes en train de lire et `README.txt` la même version mais en text pour la remise.
- `Rapport.docs` et `Rapport.pdf`, le rapport du TP.
- `main.py` le fichier qui comporte tout le code, expliqué plus haut.
- `jfreechart-stats.csv` ainsi que `tp3-enonce.pdf`, utilisé pour la réalisation de ce TP.
- `output.txt`, ce qu'affiche le progamme python.
- `todo.txt` utilisé pour l'organisation des tâches de TP
- `anthony_grange_luchino_allix-lastrego.zip` avec son dossier équivalent utilisé pour la remise.
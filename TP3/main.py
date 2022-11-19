### Modules ###

import csv
import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import spearmanr

# Pour stocker les métriques
NoCom = []
NCLOC = []
DCP = []

# Pour stocker les métriques en groupe (pour les corrélations)
NoCom_NCLOC = []
NoCom_DCP = []

### Obtention des données ###

with open("jfreechart-stats.csv", 'r') as csv_file:
    csv = csv.reader(csv_file)

    for row in csv:
        NoCom.append(row[1])
        NCLOC.append(row[2])
        DCP.append(row[3])

# Pour retirer les noms
NoCom.pop(0)
NCLOC.pop(0)
DCP.pop(0)

# Pour transformer en floats
for i in range(len(NoCom)):
    NoCom[i] = float(NoCom[i])
    NCLOC[i] = float(NCLOC[i])
    DCP[i] = float(DCP[i])
    NoCom_NCLOC.append((NoCom[i], NCLOC[i]))
    NoCom_DCP.append((NoCom[i], DCP[i]))


### distribution ###

# Pour obtenir un dictionnaire de la distribution d'une métrique
def bar(array):
    dico = {}
    for val in array:
        if val in dico.keys():
            dico[val] += 1
        else:
            dico[val] = 1
    return dico


# Pour obtenir les graphiques de distribution

plt.figure("Nocom_Bar")
plt.title("NoCom Distribution")
plt.xlabel("Valeurs")
plt.ylabel("Quantité")
dico_NoCom = bar(NoCom)
plt.bar(list(dico_NoCom.keys()), list(dico_NoCom.values()))

plt.figure("NCLOC_Bar")
plt.title("NCLOC Distribution")
plt.xlabel("Valeurs")
plt.ylabel("Quantité")
dico_NCLOC = bar(NCLOC)
plt.bar(list(dico_NCLOC.keys()), list(dico_NCLOC.values()))

plt.figure("DCP_Bar")
plt.title("DCP Distribution")
plt.xlabel("Valeurs")
plt.ylabel("Quantité")
dico_DCP = bar(DCP)
plt.bar(list(dico_DCP.keys()), list(dico_DCP.values()))

### Boites à moustaches ###

# Affichage des plots de boites à moustaches
plt.figure("NoCom")
plt.title("Boîte à moustache NoCom")
plt.ylabel("Valeurs")
bp_NoCom = plt.boxplot(NoCom, showmeans=True)

plt.figure("NCLOC")
plt.title("Boîte à moustache NCLOC")
plt.ylabel("Valeurs")
bp_NCLOC = plt.boxplot(NCLOC, showmeans=True)

plt.figure("DCP")
plt.title("Boîte à moustache DCP")
plt.ylabel("Valeurs")
bp_DCP = plt.boxplot(DCP, showmeans=True)

plt.figure("All")  # obligé d'être affichée car facilite les calculs

# Affichage des données
# source : https://towardsdatascience.com/how-to-fetch-the-exact-values-from-a-boxplot-python-8b8a648fc813

data = [NoCom, NCLOC, DCP]
bp = plt.boxplot(data, showmeans=True)
medians = [round(item.get_ydata()[0], 1) for item in bp['medians']]
means = [round(item.get_ydata()[0], 1) for item in bp['means']]
minimums = [round(item.get_ydata()[0], 1) for item in bp['caps']][::2]
maximums = [round(item.get_ydata()[0], 1) for item in bp['caps']][1::2]
q1 = [round(min(item.get_ydata()), 1) for item in bp['boxes']]
q3 = [round(max(item.get_ydata()), 1) for item in bp['boxes']]
fliers = [item.get_ydata() for item in bp['fliers']]
lower_outliers = []
upper_outliers = []
for i in range(len(fliers)):
    lower_outliers_by_box = []
    upper_outliers_by_box = []
    for outlier in fliers[i]:
        if outlier < q1[i]:
            lower_outliers_by_box.append(round(outlier, 1))
        else:
            upper_outliers_by_box.append(round(outlier, 1))
    lower_outliers.append(lower_outliers_by_box)
    upper_outliers.append(upper_outliers_by_box)

stats = [medians, means, minimums, maximums, q1, q3, lower_outliers, upper_outliers]
stats_names = ['Median', 'Mean', 'Minimum', 'Maximum', 'Q1', 'Q3', 'Lower outliers', 'Upper outliers']
categories = ['NoCom', 'NCLOC', 'DCP']
for i in range(len(categories)):
    print(f'\033[1m{categories[i]}\033[0m')
    for j in range(len(stats)):
        print(f'{stats_names[j]}: {stats[j][i]}')
    print('\n')

### Analyse de corrélation ###

# Les données sont triées mais au final ce n'est pas nécessaire.

# NoCom/NCLOC

plt.figure("NoCom/NCLOC")
plt.title("NoCom par rapport à NCLOC")
plt.xlabel("NoCom")
plt.ylabel("NCLOC")

# Tri des données
NoCom_NCLOC_S = sorted(NoCom_NCLOC)
NoCom_S = []
NCLOC_S = []

for i in range(len(NoCom_NCLOC_S)):
    if NoCom_NCLOC_S[i][1] < 1400: # on ne prend pas les points extêmes
        NoCom_S.append(NoCom_NCLOC_S[i][0])
        NCLOC_S.append(NoCom_NCLOC_S[i][1])

# regression linéaire
coef = np.polyfit(NoCom_S, NCLOC_S, 1)
poly1d_fn = np.poly1d(coef)

print(f'\033[1m{"NoCom/NCLOC :"}\033[0m')
print("\t m = " + str(coef[0]))
print("\t b = " + str(coef[1]))

plt.plot(NoCom_S, NCLOC_S, '.', NoCom_S, poly1d_fn(NoCom_S), '--k')

# NoCom/DCP

plt.figure("NoCom/DCP")
plt.title("NoCom par rapport à DCP")
plt.xlabel("NoCom")
plt.ylabel("DCP")

# Tri des données
NoCom_DCP_S = sorted(NoCom_DCP)
NoCom_S1 = []
DCP_S = []
for i in range(len(NoCom_DCP_S)):
    NoCom_S1.append(NoCom_DCP_S[i][0])
    DCP_S.append(NoCom_DCP_S[i][1])

# regression linéaire
coef = np.polyfit(NoCom, DCP, 1)
poly1d_fn = np.poly1d(coef)

print(f'\033[1m{"NoCom/DCP :"}\033[0m')
print("\t m = " + str(coef[0]))
print("\t b = " + str(coef[1]) + "\n")

plt.plot(NoCom_S1, DCP_S, '.', NoCom_S1, poly1d_fn(NoCom_S1), '--k')

plt.show()  # Affichage des plots

### Spearman ###

# Appel à spearmanr de scipy

print(f'\033[1m{"NoCom/NCLOC :"}\033[0m')
print("p=" + str(spearmanr(NoCom, NCLOC)[0]) + "\n")

print(f'\033[1m{"NoCom/DCP :"}\033[0m')
print("p=" + str(spearmanr(NoCom, DCP)[0]) + "\n")

### Modules ###

import csv
import matplotlib.pyplot as plt
import numpy as np

NoCom = []
NCLOC = []
DCP = []

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


### Boites à moustaches ###

# Affichage des plots
plt.figure("NoCom")
bp_NoCom = plt.boxplot(NoCom, showmeans=True)

plt.figure("NCLOC")
bp_NCLOC = plt.boxplot(NCLOC, showmeans=True)

plt.figure("DCP")
bp_DCP = plt.boxplot(DCP, showmeans=True)

plt.figure("All")

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

# NoCom/NCLOC

plt.figure("NoCom/NCLOC")

coef = np.polyfit(NoCom, NCLOC, 1)
poly1d_fn = np.poly1d(coef)

print(f'\033[1m{"NoCom/NCLOC :"}\033[0m')
print("\t m = " + str(coef[0]))
print("\t b = " + str(coef[1]))

plt.plot(NoCom, NCLOC, '.', NoCom, poly1d_fn(NoCom), '--k')

# NoCom/DCP

plt.figure("NoCom/DCP")

coef = np.polyfit(NoCom, DCP, 1)
poly1d_fn = np.poly1d(coef)

print(f'\033[1m{"NoCom/NCLOC :"}\033[0m')
print("\t m = " + str(coef[0]))
print("\t b = " + str(coef[1]))

plt.plot(NoCom, DCP, '.', NoCom, poly1d_fn(NoCom), '--k')

plt.show()

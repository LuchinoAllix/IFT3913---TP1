package org.example;

import java.io.*;

/* todo */
public class CSEC {

    /* todo */
    public static String csec(String csv) {
        String result = "";
        String[] csvEntries = csv.split("\n");
        // Creation matrice csec[x][a, b] ou x est la ligne, a le nom de
        // la class associé et b les classes couplées
        String[][] csec = new String[csvEntries.length][2];
        // Initialisation des valeurs de scec
        for (int i = 0; i < csvEntries.length; i++) {
            csec[i][0] = csvEntries[i].split(",")[2];
            csec[i][1] = "";
        }

        // Pour chaque entrée du fichier csv, lecture du fichier concerné
        // et regarde si une autre class présente dans le tableau deps est mentionné
        // Si oui la class est ajouté au couplage du fichier concerné et inversement
        // dans csec sauf si ils ont déjà été ajouté
        for (int x = 0; x < csvEntries.length; x++) {
            try {
                File file = new File(csvEntries[x].split(",")[0]);
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                String line;
                while((line = br.readLine()) != null) {
                    for (int y = 0; y < csvEntries.length; y++) {
                        if ((x != y) && line.contains(csec[y][0])) {
                            if (!csec[x][1].contains(csec[y][0])) {
                                if (csec[x][1].equals("")) {
                                    csec[x][1] = csec[y][0];
                                } else {
                                    csec[x][1] = csec[x][1] + "/" + csec[y][0];
                                }
                            }
                            if(!csec[y][1].contains(csec[x][0])) {
                                if (csec[y][1].equals("")) {
                                    csec[y][1] = csec[x][0];
                                } else {
                                    csec[y][1] = csec[y][1] + "/" + csec[x][0];
                                }
                            }
                        }
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(" - Error - ");
            }
        }

        // Update du fichier csv avec les nouvelles valeurs du calcul de csec dans la variable result
        for (int z = 0; z < csvEntries.length; z++) {
            result += csvEntries[z] + "," + (csec[z][1].split("/").length) + "\n";
        }
        return result;
    }
}

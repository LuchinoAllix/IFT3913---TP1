package org.example;

import com.github.javaparser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;


/* Fichier principal du TP2.
* C'est depuis ici que le programme se lance.
* Le chemin du dossier à analyser est transformé en csv puis donné
* aux différentes classes pour obtenir les métriques qui seront à leur tour
* ajouté dans le fichier csv qui sera produit.
* Finalement applique les calculs grâce aux métriques pour répondre aux questions du GQM*/
public class Main {

    /* Récupéré du TP1, permet de trouver tous les fichiers java et les mets
    * sous forme de String en format csv (filePath, module, filename).*/
    public static String jls(String path) {
        File file = new File(path);
        return jlsRec(file,file.getName());
    }

    /* Partie récursive de jls (également récupérée du TP1) */
    public static String jlsRec(File path, String module) {
        StringBuilder temp = new StringBuilder();

        // On regarde tous les fichiers :
        for (final File file : Objects.requireNonNull(path.listFiles())) {

            // Si on regarde un dossier, on fait un appel récursif
            if (file.isDirectory()) {
                temp.append(jlsRec(file, module + "." + file.getName()));
            } else if (file.isFile()) { // Si c'est un fichier '.java', on prend les informations nécessaires
                int len = file.getName().length();
                if (len > 5 && file.getName().substring(len-5).equals(".java")){
                    temp.append(file.getPath()).append(",")
                            .append(module).append(",")
                            .append(file.getName().substring(0,len - 5)).append("\n");
                }
            }
        }
        return temp.toString();
    }

    /* Separatse methods to calculate PMNT and TPC once because pmnt already outputs the
     * csv file augmented with pmnt and tpc values */
    public static String addPMNTToCSV(String csv){
        return PMNT.pmnt(csv);
    }

    /* Separates methods to calculate csec once because csec already outputs the csv
    * file augmented with csec values  */
    public static String addCSECToCSV(String csv) {
        return CSEC.csec(csv);
    }

    /* Méthode finale qui permet d'ajouter toutes les métriques restantes au string csv ainsi
    *  qu'une ligne de titre pour chaque colonne :
    *  (filePath, module, fileName, pmnt, tpcbis, csec, wmc, rfc, dc, loc, cloc). */
    public static String addMetricsToCSV(String csv) {
        // On ajoute les noms des colonnes
        StringBuilder result = new StringBuilder("filePath,module,fileName,pmnt,tpcbis,csec,wmc,rfc,dc,loc,cloc\n");
        String[] csvEntries = csv.split("\n");

        // Pour chaque fichier, on calcule la métrique et on la rajoute au fichier
        for (String csvEntry : csvEntries) {
            try {
                String filePath = csvEntry.split(",")[0];
                float[] dc = DC.dc(filePath);
                result.append(csvEntry).append(",")
                        .append(WMC.wmc(filePath)).append(",")
                        .append(RFC.rfc(filePath)).append(",")
                        .append(dc[0]).append(",")
                        .append(dc[1]).append(",")
                        .append(dc[2]).append(",")
                        .append("\n");
            } catch (IOException | ParseException e) {
                System.out.println("Error : Parsing error while calculating metrics");
            }
        }

        return result.toString();
    }

    /* Crée le fichier csv (ou remplace celui déjà existant) et le remplie avec la String donné en argument */
    public static void writeToFile(String content, String fileName) {
        try {
            // Création nouveau fichier ou remplacement de l'ancien
            File output = new File(fileName);
            output.createNewFile();

            // Ecriture sur le nouveau fichier
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            System.out.println(" - Error - : could not write to the desired file.");
        }
    }

    /* Retire les fichiers test du csv pour la suite des calculs qui répondent aux questions */
    public static String removeTestLinesFromCsv(String csv) {
        StringBuilder result = new StringBuilder("");
        String[] csvEntries = csv.split("\n");

        for (String csvEntry : csvEntries) {
            if (!csvEntry.split(",")[2].contains("Test")) {
                result.append(csvEntry).append("\n");
            }
        }

        return result.toString();

    }

    public static Boolean answerQ1(String csv) {
        // Init deux tableaux de complexité et cloc
        ArrayList<Float> compl = new ArrayList<>();
        ArrayList<Float> cloc = new ArrayList<>();
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");

        // Pour chaque ligne du csv ajouter WMC+RFC à compl[] et cloc à cloc[]
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            float complF = Float.parseFloat(line[6])+ Float.parseFloat(line[7]);
            float clocF = Float.parseFloat(line[10]);
            compl.add(complF);
            cloc.add(clocF);
        }

        // Trie les tableaux compl[] et cloc[] dans les tableaux complS[] et clocS[] respectivement
        ArrayList<Float> complS = new ArrayList<>(compl);
        complS.sort(null);
        ArrayList<Float> clocS = new ArrayList<>(cloc);
        clocS.sort(null);
        //System.out.println(complS);
        //System.out.println(clocS);

        // TODO commentaires
        int count = 0;
        int size = compl.size();

        // TODO commentaires
        for (int i = 0; i < size; i++) {

            int complIndexMin = 0;
            int complIndexMax = 0;
            Float clocMin;
            Float clocMax;

            for (int j = 0; j < size; j++) {
                if (Objects.equals(compl.get(i), complS.get(j))) {
                    complIndexMin = j;
                    complIndexMax = j;
                    // Si jamais il y a plusieurs fois la même valeur
                    while (j < (size - 1) && Objects.equals(compl.get(i),complS.get(j+1))) {
                        complIndexMax++;
                        j++;
                    }
                    break;
                }
            }

            int indexClocMin = complIndexMin - (size) / 5;
            if (indexClocMin < 0) indexClocMin = 0;
            clocMin = clocS.get(indexClocMin);
            int indexClocMax = complIndexMax + (size) / 5;
            if (indexClocMax > size) indexClocMax = size - 1;
            clocMax = clocS.get(indexClocMax);
            // Si cloc est entre le min/max alors l'ajouter au compteur
            if( cloc.get(i) > clocMax || cloc.get(i) < clocMin){
                count++;
            }

            System.out.println("complexity : " + compl.get(i) // TODO remove
                    + " -> clocMin : " + clocMin
                    + " -> cloc : " + cloc.get(i)
                    + " -> clocMax : " + clocMax);
        }
        System.out.println("compteur : " + count); // TODO remove
        System.out.println("taille : " + size); // TODO remove

        return count <= compl.size() / 10; // TODO commentaire explication calcul resultat
    }

    public static Boolean answerQ2(String csv) {
        return false;
    }

    public static Boolean answerQ3(String csv) {
        return false;
    }

    public static Boolean answerQ4(String csv) {
        return false;
    }

    /* Emplacement du lancement du programme du TP2 */
    public static void main(String[] args) {
        System.out.println("Commencing analysis...");
        if (args.length != 1) {
            System.out.println("Error : 1 argument is needed");
            System.out.println("Please try again with a folder path");
        } else {
            // path of the folder
            String folderPath = args[0];

            // create a String in csv format with filepath,module,fileName
            String csv = jls(folderPath);

            /* Calculates pmnt and tpc value for each file inside the csv and output the augmented jls csv file
             * result is a csv format with filepath,module,filename,csec,pmnt,tpc */
            csv = addPMNTToCSV(csv);

            // Remove all the test classes from the result before calculating the other metrics
            csv = removeTestLinesFromCsv(csv);

            /* Calculates csec value for each file inside the csv and output the augmented jls csv file
             * result is a csv format with filepath,module,filename,csec */
            csv = addCSECToCSV(csv);

            /* Calculates all other metrics for each file inside the csv and output the augmented csv file
             * result is a csv format with filePath,module,fileName,csec,pmnt,tpcbis,wmc,rfc,tpc,dc */
            csv = addMetricsToCSV(csv);

            // write result to a csv file
            writeToFile(csv, "output.csv");
            System.out.println("Le fichier output.csv a bien été crée.");

            // Question 1 du GQM :
            System.out.println("Réponse question 1 GQM : " + answerQ1(csv));
            System.out.println("Réponse question 2 GQM : " + answerQ2(csv));
            System.out.println("Réponse question 3 GQM : " + answerQ3(csv));
            System.out.println("Réponse question 4 GQM : " + answerQ4(csv));
        }
    }
	
}

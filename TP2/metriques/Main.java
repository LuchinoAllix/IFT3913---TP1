package org.example;

import com.github.javaparser.ParseException;

import java.io.*;
import java.util.Objects;

/* Fichier principal du TP2.
* C'est depuis ici que le programme se lance.
* Ici que le chemin du dossier à analyser est transformé en csv puis donné
* aux différentes classes pour obtenir les métriques qui seront à leur tour
* ajoutées dans le fichier csv qui sera produit.*/
public class Main {

    /* Récupéréé du TP1, permet de trouver tous les fichiers java et les mets
    * sous forme de String.*/
    public static String jls(String path) {
        File file = new File(path);
        return jlsRec(file,file.getName());
    }

    /* Partie récursive de jls (également récupérée du TP1 */
    public static String jlsRec(File path, String module) {
        StringBuilder temp = new StringBuilder();

        // On regarde tous les fichiers :
        for (final File file : Objects.requireNonNull(path.listFiles())) {

            // Si on regarde un dossier on fait un appel récursif
            if (file.isDirectory()) {
                temp.append(jlsRec(file, module + "." + file.getName()));
            } else if (file.isFile()) { // Si c'est un fichier '.java' on prend les informations nécessaires
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


    /* Separates methods to calculate csec once because csec already outputs the csv
    * file augmented with csec values */
    public static String addCSECToCSV(String csv) {
        return CSEC.csec(csv);
    }

    /* Separatse methods to calculate PMNT and TPC once because pmnt already outputs the
    * csv file augmented with pmnt and tpc values */
    public static String addPMNTToCSV(String csv){
        return PMNT.pmnt(csv);
    }

    /* Méthode qui permet d'ajouter toutes les métriques restante au string csv.*/
    public static String addMetricsToCSV(String csv) {
        // On ajoute les noms des colonnes
        StringBuilder result = new StringBuilder("filePath,module,fileName,pmnt,tpcbis,csec,wmc,rfc,dc\n");
        String[] csvEntries = csv.split("\n");

        // Pour chaque fichier on calcule la métrique et on la rajoute au fichier
        for (String csvEntry : csvEntries) {
            try {
                String filePath = csvEntry.split(",")[0];
                result.append(csvEntry).append(",")
                        .append(WMC.wmc(filePath)).append(",")
                        .append(RFC.rfc(filePath)).append(",")
                        .append(DC.dc(filePath)).append(",")
                        .append("\n");
            } catch (IOException | ParseException e) {
                System.out.println("Error : Parsing error while calculating metrics");
            }
        }

        return result.toString();
    }

    /* Crée le fichier csv (ou remplace celui déjà existant) */
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

    /* Emplacement du lancement du programme du TP2 */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Error : 1 argument is needed");
            System.out.println("Please try again with a folder path");
        } else {
            // path of the folder
            String folderPath = args[0];
            //String folderPath = "/Users/anthony/Desktop/jfreechart-master";
            //String folderPath2 = "C:\\Users\\luchi\\Desktop\\jfreechart";

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
        }
    }
	
}

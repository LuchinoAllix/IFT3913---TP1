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
        StringBuilder result = new StringBuilder("filePath,module,fileName,pmnt,tpcbis,csec,wmc,rfc,dc,loc,cloc,lcom\n");
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
                        .append(Lcom.lcom(filePath))
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

        // compteur de fichier dont le rang de complexité n'est pas égal à celui de son cloc à 20% près
        int count = 0;
        // nombre de fichiers total du csv
        int size = compl.size();

        for (int i = 0; i < size; i++) {

            int complIndexMin = 0;
            int complIndexMax = 0;
            Float clocMin;
            Float clocMax;

            // On cherche l'index de compl[i] dans complS et on init indexMin et indexMax en prennant en compte les
            // valeurs identiques.
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

            int seuilMin = (complIndexMin - (size) / 5);
            int indexClocMin = Math.max(seuilMin, 0);

            int seuilMax = complIndexMax + (size) / 5;
            int indexClocMax = seuilMax > size ? size - 1 : seuilMax;

            clocMin = clocS.get(indexClocMin);
            clocMax = clocS.get(indexClocMax);
            // Si cloc n'est pas entre le min/max alors l'ajouter au compteur
            if( cloc.get(i) > clocMax || cloc.get(i) < clocMin){
                count++;
            }
        }

        // true si 10% ou moins des fichiers ne sont pas à 20% d'écart en termes de rang de WMC+RFC et CLOC
        return count <= compl.size() / 10;
    }

    public static Boolean answerQ2(String csv) {
        // Init deux tableaux de csec et lcom
        ArrayList<Float> csec = new ArrayList<>();
        ArrayList<Float> lcom = new ArrayList<>();
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");

        // Pour chaque ligne du csv ajouter csec à csec[] et lcom à lcom[]
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            float csecF = Float.parseFloat(line[5]);
            float lcomF = Float.parseFloat(line[11]);
            csec.add(csecF);
            lcom.add(lcomF);
        }

        // Trie les tableaux csec[] et lcom[] dans les tableaux csecS[] et lcomS[] respectivement
        ArrayList<Float> csecS = new ArrayList<>(csec);
        csecS.sort(null);
        ArrayList<Float> lcomS = new ArrayList<>(lcom);
        lcomS.sort(null);

        // compteur de fichier dont le rang de csec n'est pas égal à celui de son lcom à 20% près
        int count = 0;
        // nombre de fichiers total du csv
        int size = csec.size();

        for (int i = 0; i < size; i++) {

            int csecIndexMin = 0;
            int csecIndexMax = 0;
            Float lcomMin;
            Float lcomMax;

            // On cherche l'index de csec[i] dans csecS et on init indexMin et indexMax en prennant en compte les
            // valeurs identiques.
            for (int j = 0; j < size; j++) {
                if (Objects.equals(csec.get(i), csecS.get(j))) {
                    csecIndexMin = j;
                    csecIndexMax = j;
                    // Si jamais il y a plusieurs fois la même valeur
                    while (j < (size - 1) && Objects.equals(csec.get(i),csecS.get(j+1))) {
                        csecIndexMax++;
                        j++;
                    }
                    break;
                }
            }

            int seuilMin = (csecIndexMin - (size) / 5);
            int indexLcomMin = Math.max(seuilMin, 0);

            int seuilMax = (csecIndexMax + (size) / 5);
            int indexLcomMax = seuilMax > size ? size - 1 : seuilMax;

            lcomMin = lcomS.get(indexLcomMin);
            lcomMax = lcomS.get(indexLcomMax);
            // Si lcom n'est pas entre le min/max alors l'ajouter au compteur
            if( lcom.get(i) > lcomMax || lcom.get(i) < lcomMin){
                count++;
            }
        }

        // true si 10% ou moins des fichiers ne sont pas à 20% d'écart en termes de rang de csec et lcom
        return count <= csec.size() / 10;
    }

    public static Boolean answerQ3(String csv) {
        return false;
    }

    public static Boolean answerQ4(String csv) {
        // Init deux tableaux de complexité et cloc
        ArrayList<Float> pmnt = new ArrayList<>();
        ArrayList<Float> compl = new ArrayList<>();
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");
        float q1Compl;
        int count = 0;

        // Pour chaque ligne du csv ajouter WMC+RFC à compl[] et cloc à cloc[]
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            float complF = Float.parseFloat(line[5])+ Float.parseFloat(line[7]);
            float pmntF = Float.parseFloat(line[3].substring(0,line[3].length()-1));
            compl.add(complF);
            pmnt.add(pmntF);
        }

        ArrayList<Float> complS = new ArrayList<>(compl);
        complS.sort(null);
        q1Compl = complS.get(complS.size()/4);

        for (int i = 0; i < compl.size(); i++) {
            if(pmnt.get(i)>75 && compl.get(i)>q1Compl){
                count++;
            }
        }

        return count <= compl.size() / 10;
    }

    /* Emplacement du lancement du programme du TP2 */
    public static void main(String[] args) {
        System.out.println("Commencing metric analysis...");
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
            System.out.println("Metric analysis finished");

            System.out.println("Commencing GQM analysis...");
            // Questions du GQM :
            System.out.println("Réponse question 1 GQM : " + answerQ1(csv));
            System.out.println("Réponse question 2 GQM : " + answerQ2(csv));
            System.out.println("Réponse question 3 GQM : " + answerQ3(csv));
            System.out.println("Réponse question 4 GQM : " + answerQ4(csv));
        }
    }
	
}

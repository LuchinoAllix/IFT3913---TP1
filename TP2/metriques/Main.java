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


    /*
     * Description :
     * ----------
     * Récupéré du TP1, permet de trouver tous les fichiers java et les mets
     * sous forme de String en format csv (filePath, module, filename).
     *
     * Entrées :
     * ----------
     *   path (String) : String de l'emplacement des fichier à regarder.
     *
     * Sortie :
     * ----------
     *  String : le fichier csv en string.
     *
     * Informations supplémentaires :
     * ----------
     * Fait appel à jlsRec récursivement.
     * */
    public static String jls(String path) {
        File file = new File(path);
        return jlsRec(file,file.getName());
    }

    /*
     * Description :
     * ----------
     * Partie recursive de jls, défini dans src dans le TP1.
     *
     * Entrées :
     * ----------
     *   path (Fichier) : Fichier à regarder.
     *  module (string) : Module de la classe.
     *
     * Sortie :
     * ----------
     *  String : le fichier csv en string augmené de la classe.
     * */
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

    /*
     * Description :
     * ----------
     * Permet de calculer et ajouter pmnt et tpc séparement que depuis addMetricsToCSV.
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques.
     *
     * Sortie :
     * ----------
     *  String : le fichier csv en string augmenté de csec.
     *
     * Informations supplémentaires :
     * ----------
     * Appel à pmnt de la classe PMNT.
     * */
    public static String addPMNTToCSV(String csv){
        return PMNT.pmnt(csv);
    }

    /*
     * Description :
     * ----------
     * Permet de calculer et ajouter csec séparement que depuis addMetricsToCSV
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques.
     *
     * Sortie :
     * ----------
     *  String : le fichier csv en string augmenté de csec.
     *
     * Informations supplémentaires :
     * ----------
     * Appel à csec de la classe CSEC.
     * */
    public static String addCSECToCSV(String csv) {
        return CSEC.csec(csv);
    }

    /*
     * Description :
     * ----------
     * Permet d'ajouter toutes les métriques restantes au string csv ainsi
     *  qu'une ligne de titre pour chaque colonne :
     * (filePath, module, fileName, pmnt, tpcbis, csec, wmc, rfc, dc, loc, cloc,lcom)
     * (restantes car pmnt, tpc et csec sont ajoutée précément par addPMNTToCSV et addCSECToCSV).
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques.
     *
     * Sortie :
     * ----------
     *   String : csv sous forme de String.
     *
     * Informations supplémentaires :
     * ----------
     * */
    public static String addMetricsToCSV(String csv) {
        // On ajoute les noms des colonnes
        StringBuilder result = new StringBuilder("filePath,module,fileName,pmnt,tpcbis,csec,wmc,rfc,dc,loc,cloc,lcom2\n");
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
                        .append(LCOM.lcom(filePath))
                        .append("\n");
            } catch (IOException | ParseException e) {
                System.out.println("Error : Parsing error while calculating metrics");
            }
        }

        return result.toString();
    }

    /*
     * Description :
     * ----------
     * Crée le fichier csv (ou remplace celui déjà existant) et le remplie
     * avec la String donné en argument.
     *
     * Entrées :
     * ----------
     *   content (String) : Chaine de caractère qu'il faut écrire sur le fichier.
     *   fileName (String) : nom du fichier sue l'on veut créer/remplacer.
     *
     * Informations supplémentaires :
     * ----------
     * Effet de bord : création/remplacement du fichier et affichage d'erreur.
     * */
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

    /*
     * Description :
     * ----------
     * Retire les fichiers test du csv pour la suite des calculs qui répondent
     * aux questions.
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques (voir
     *   addMetricsToCSV(String csv) pour les informations sur les métriques.
     *
     * Sortie :
     * ----------
     *   String : la même chaine de caractère d'entrée mais sans les fichiers tests.
     * */
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

    /*
     * Description :
     * ----------
     * Permet de répondre à la question 1 du tp, à savoir : "Le niveau de
     * documentation des classes est-il approprié par rapport à leur complexité ?".
     * En regardant les métriques cloc et wmc (avec cc) lcom répondont à la
     * question si au moins 90% des fichiers passent le test. C'est-à-dire
     * si les méthodes les plus complexes ont un niveau de documentation adapté.
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques (voir
     *   addMetricsToCSV(String csv) pour les informations sur les métriques.
     *
     * Sortie :
     * ----------
     *   Boolean : true si au moins 90 des classes sont considérées comme ayant
     *   suffisament de documentation pour leur complexité et false sinon.
     *
     * Informations supplémentaires :
     * ----------
     * Effet de bord : affichage de la réponse.
     * */
    public static Boolean answerQ1(String csv) {
        // Init deux listes de complexité et cloc
        ArrayList<Float> compl = new ArrayList<>();
        ArrayList<Float> cloc = new ArrayList<>();
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");

        // Pour chaque ligne du csv ajouter WMC+RFC à compl<> et cloc à cloc<> (<> pour listes)
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            float complF = Float.parseFloat(line[6])+ Float.parseFloat(line[7]);
            float clocF = Float.parseFloat(line[10]);
            compl.add(complF);
            cloc.add(clocF);
        }

        // Trie les listes compl<> et cloc<> dans les listes complS<> et clocS<> respectivement (<> pour listes)
        ArrayList<Float> complS = new ArrayList<>(compl);
        complS.sort(null);
        ArrayList<Float> clocS = new ArrayList<>(cloc);
        clocS.sort(null);

        // Initialisation de count, le nombre de fichiers qui ne respecte pas les critères de facilité d'analyse
        int count = 0;
        // size pour simplifier la lecture, vu que la taille ne change pas
        int size = compl.size();

        for (int i = 0; i < size; i++) {
            /* Respectivement l'indice le plus bas et le plus haut des éléments de complS[]
            qui vallent compl[i] (si cet élément est unique les deux valeurs sont égales). */
            int complIndexMin = 0;
            int complIndexMax = 0;
            /* Respectivement la valeur la plus petite et la plus grande correspondant à
            clocS[complIndexMin] et cloc[complIndexMax] */
            Float clocMin;
            Float clocMax;

            /* On trouve les valeurs de complIndexMin et complIndexMax */
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

            /* On a choisi un seuil (ici 20), qui représente le pourcentage de classes dont le cloc
            est considéré comme satisfaisant pour valider la facilité d'analyse en fonction de la
            complexité (compl). Il suffit d'utiliser le seuil pour obtenir la marge d'index à
            partir de complIndexMin et complIndexMax, une fois la marge d'index obtenue, on
            calcule les valeurs cloc limites (clocMin et clocMax) et on vérifie si le cloc de la
            classe est dedans.*/
            int seuil = 20;
            int seuilMin = complIndexMin - (size) * seuil / 100;
            int indexClocMin = Math.max(seuilMin, 0);

            int seuilMax = complIndexMax + (size) * seuil / 100;;
            int indexClocMax = (seuilMax > size) ? size - 1 : seuilMax;

            clocMin = clocS.get(indexClocMin);
            clocMax = clocS.get(indexClocMax);
            // Si cloc est entre le min/max alors l'ajouter au compteur
            if( cloc.get(i) > clocMax || cloc.get(i) < clocMin){
                count++;
            }
        }

        /* Si le pourcentage de classes dont la facilité d'analyse n'est pas atteinte
        pour au plus 10% des classes, alors la documentation du code n'est pas considérée comme
        adaptée à la complexité.*/
        return count <= size / 10;
    }

    /*
    * Description :
    * ----------
    * Permet de répondre à la question 2 du tp, à savoir : "La conception est-elle
    * bien modulaire ?". En regardant les métriques csec et lcom nous répondont à la
    * question si au moins 90% des fichiers passent le test.
    *
    * Entrées :
    * ----------
    *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
    *   noms des classes, leur module, leur emplacement et leur métriques (voir
    *   addMetricsToCSV(String csv) pour les informations sur les métriques.
    *
    * Sortie :
    * ----------
    *   Boolean : true si au moins 90 des classes sont considérée comme modulaires
    *   et false sinon.
    *
    * Informations supplémentaires :
    * ----------
    * Effet de bord : affichage de la réponse.
    * */
    public static Boolean answerQ2(String csv) {
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");

        // Init des compteurs de fichiers qui respectent la condition de mesure
        int countCouplage = 0;
        int countCohesion = 0;

        // Récupération des valeurs csec et lcom des fichiers à partir du csv
        ArrayList<Integer> csec = new ArrayList<>();
        ArrayList<Integer> lcom = new ArrayList<>();
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            csec.add(Integer.parseInt(line[5]));
            lcom.add(Integer.parseInt(line[11]));
        }

        // size pour simplifier la lecture, vu que la taille ne change pas
        int size = csec.size();

        // Pour chaque fichier ajoute 1 au compteur de couplage si le couplage est plus petit que 10% du nombre total
        // de fichiers dans le csv.
        for (int i = 0; i < size; i++) {
            if (csec.get(i) <= size / 10 ) {
                countCouplage++;
            }
        }
        // mesure de couplage accepté si plus de 90% des fichiers ont un couplage acceptable
        boolean couplageRes = countCouplage >= size * 0.9;

        // Pour chaque fichier ajoute 1 au compteur de cohésion si LCOM2 est plus petite que 1
        // ce qui signifie que les méthodes sont majoritairement cohésives.
        for (int i = 0; i < size; i++) {
            if (lcom.get(i) < 1 ) {
                countCohesion++;
            }
        }
        // mesure de cohésion acceptée si plus de 90% des fichiers ont une cohésion acceptable
        boolean cohesionRes = countCohesion >= size * 0.9;

        return couplageRes && cohesionRes;
    }

    /*
     * Description :
     * ----------
     * Permet de répondre à la question 3 du tp, à savoir : "Le code est-il mature ?".
     * En regardant les métriques tpc, csec et rfc nous répondont à la
     * question si au moins 90% des fichiers passent le test.
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques (voir
     *   addMetricsToCSV(String csv) pour les informations sur les métriques.
     *
     * Sortie :
     * ----------
     *   Boolean : true si au moins 90 des classes sont considérée comme stables
     *   et false sinon.
     *
     * Informations supplémentaires :
     * ----------
     * Effet de bord : affichage de la réponse.
     * */
    public static Boolean answerQ3(String csv) {
        // Init deux listes de csec et lcom
        ArrayList<Float> tpc = new ArrayList<>();
        ArrayList<Float> compl = new ArrayList<>();
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");
        int count = 0;

        // Pour chaque ligne du csv ajouter WMC+RFC à compl<> et tpc à tpc<> (<> pour liste)
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            float complF = Float.parseFloat(line[5])+ Float.parseFloat(line[7]);
            float tpcF = Float.parseFloat(line[4]);
            compl.add(complF);
            tpc.add(tpcF);
        }

        // On trie les lites
        ArrayList<Float> complS = new ArrayList<>(compl);
        complS.sort(null);
        ArrayList<Float> tpcS = new ArrayList<>(tpc);
        tpcS.sort(null);

        /* On choisi un seuil et on calcul les valeurs critique pour que
        le code soit considéré comme stable */
        int seuil = compl.size()*9/10;
        float complCrit = complS.get(seuil);
        float tpcCrit = tpcS.get(seuil);

        for (int i = 0; i < compl.size(); i++) {
            if(compl.get(i)>complCrit && tpc.get(i)>tpcCrit) count++;
        }

        /* Si le pourcentage de classes dont la stabilité n'est pas considérée
        comme suffisante n'est pas atteinte pour au plus 10% des classes, alors le code
        n'est pas considéré comme stable. */
        return count <= compl.size() / 10;
    }

    /*
     * Description :
     * ----------
     * Permet de répondre à la question 4 du tp, à savoir : "Le code peut-il être bien
     * testé automatiquement ?". En regardant les métriques csec, rfc et pmnt nous
     * répondont à la question si au moins 90% des fichiers passent le test.
     *
     * Entrées :
     * ----------
     *   csv (String) : Chaine de caractère du fichier csv qui contient tous les
     *   noms des classes, leur module, leur emplacement et leur métriques (voir
     *   addMetricsToCSV(String csv) pour les informations sur les métriques.
     *
     * Sortie :
     * ----------
     *   Boolean : true si au moins 90 des classes sont considérée pouvant être
     *   testée automatiquement et false sinon.
     *
     * Informations supplémentaires :
     * ----------
     * Effet de bord : affichage de la réponse.
     * */
    public static Boolean answerQ4(String csv) {
        // Init deux listes de pmnt et de complexité
        ArrayList<Float> pmnt = new ArrayList<>();
        ArrayList<Float> compl = new ArrayList<>();
        // csvSplit contient chaque ligne du csv
        String[] csvSplit = csv.split("\n");
        // Médiane
        float q1Compl;
        /* Nombre de classes dont les tests ne sont pas considérés comme
        pouvant être fait automatiquement */
        int count = 0;

        // Pour chaque ligne du csv ajouter CSEC+RFC à compl<> et pmnt à pmnt<> (<> pour liste)
        for (int i = 1; i < csvSplit.length; i++) {
            String[] line = csvSplit[i].split(",");
            float complF = Float.parseFloat(line[5])+ Float.parseFloat(line[7]);
            float pmntF = Float.parseFloat(line[3].substring(0,line[3].length()-1));
            compl.add(complF);
            pmnt.add(pmntF);
        }

        // création de complS qui est compl<> mais triée (<> pour liste)
        ArrayList<Float> complS = new ArrayList<>(compl);
        complS.sort(null);
        q1Compl = complS.get((complS.size()/2)); // calcul de la médiane

        /*Selon un critère de séléction, on regarde quelle classe est considérée comme
        pouvant être testeé automatiquement les détails du critère et dans le rapport */
        for (int i = 0; i < compl.size(); i++) {
            if(pmnt.get(i)>90 && compl.get(i)>q1Compl){
                count++;
            }
        }

        /* Si le pourcentage de classes dont l'automatisation des tests n'est pas
        possible n'est pas atteinte pour au plus 10% des classes, alors le code
        n'est pas considéré comme pouvant être testé automatiquement. */
        return count <= compl.size() / 10;
    }

    /*
     * Description :
     * ----------
     * Emplacement de lancement du programme du TP2, affiche les étapes au fur
     * et à mesur de l'exécution, et les réponses au question 1 à 4 du TP.
     * S'il n'y a pas un emplacment de fichier en argument de lancement ou s'il y
     * un quelconque problème lors de l'exécution un message d'erreur sera affiché.
     *
     * Entrées :
     * ----------
     *   argr (String[]) : Tableau de chaines de caractères prise sur le lancement du programe
     *
     * Informations supplémentaires :
     * ----------
     * Effet de bord : affichage de la réponse et appel à des méthodes qui ont des
     * effets de bords.
     * */
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
            System.out.println("The file output.csv has been created (or put up to date if already existing).");
            System.out.println("Metric analysis finished.\n");

            System.out.println("Commencing GQM analysis...");
            // Questions du GQM :
            System.out.println("Réponse question 1 GQM : " + answerQ1(csv));
            System.out.println("Réponse question 2 GQM : " + answerQ2(csv));
            System.out.println("Réponse question 3 GQM : " + answerQ3(csv));
            System.out.println("Réponse question 4 GQM : " + answerQ4(csv));

            System.out.println("GQM analysis finished.");
        }
    }
	
}

package org.example;

import com.github.javaparser.ParseException;

import java.io.*;
import java.util.Objects;

public class Main {

    public static String jls(String path) {
        File file = new File(path);
        return jlsRec(file,file.getName());
    }

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
                            .append(file.getName().substring(len - 5)).append("\n");
                }
            }
        }
        return temp.toString();
    }


    // Separate method to calculate csec once because csec already output the csv file augmented with csec values
    public static String addCSECToCSV(String csv) {
        return CSEC.csec(csv);
    }

    public static String addPMNTToCSV(String csv){
        return PMNT.pmnt(csv);
    }

    public static String addMetricsToCSV(String csv) {
        StringBuilder result = new StringBuilder("filePath,module,fileName,csec,pmnt,tpcbis,wmc,rfc,tpc,dc,dt,cc\n");
        String[] csvEntries = csv.split("\n");

        for (String csvEntry : csvEntries) {
            try {
                String filePath = csvEntry.split(",")[0];
                result.append(csvEntry).append(",")
                        .append(WMC.wmc(filePath)).append(",")
                        .append(RFC.rfc(filePath)).append(",")
                        .append(TPC.tpc(filePath)).append(",")
                        .append(DC.dc(filePath)).append(",")
                        .append(DT.dt(filePath)).append(",")
                        .append(CC.cc(filePath)).append(",") // todo
                        .append("\n");
            } catch (IOException | ParseException e) {
                System.out.println("Error : Parsing error while calculating metrics");
            }
        }

        return result.toString();
    }

    public static Boolean writeToFile(String content, String fileName) {
        boolean res = false;
        try {
            // Création nouveau fichier ou remplacement de l'ancien
            File output = new File(fileName);
            res = output.createNewFile();

            // Ecriture sur le nouveau fichier
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            System.out.println(" - Error - : could not write to the desired file.");
        }
        return res;
    }

    public static void main(String[] args) {

        // path of the folder
        //String folderPath = "/Users/anthony/Desktop/jfreechart-master";
        String folderPath2 = "C:\\Users\\luchi\\Desktop\\jfreechart";

        // create a String in csv format with filepath,module,fileName
        String csv = jls(folderPath2);
        // calculate csec value for each file inside the csv and output the augmented jls csv file
        // result is a csv format with filepath,module,filename,csec
        String csvCsec = addCSECToCSV(csv);
        // calculate all other metrics for each file inside the csv and output the augmented csv file
        // result is a csv format with filePath,module,fileName,csec,wmc,rfc,tpc,dt,dc
        String csvCsecPmnt = addPMNTToCSV(csvCsec);
        String result = addMetricsToCSV(csvCsecPmnt);
        // write result to a csv file
        boolean res = writeToFile(result, "output.csv");
        if(res) {
            System.out.println("Le fichier output.csv a bien été crée.");
        } else{
            System.out.println("Le fichier output.csv n'a pas pu été crée.");
        }

    }
	
}

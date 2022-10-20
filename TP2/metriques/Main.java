package org.example;

import com.github.javaparser.ParseException;

import java.io.*;

public class Main {

    public static String jls(String path) {
        File file = new File(path);
        return jlsRec(file,file.getName());
    }

    public static String jlsRec(File path, String module) {
        String temp = "";

        // On regarde tous les fichiers :
        for (final File file : path.listFiles()) {

            // Si on regarde un dossier on fait un appel récursif
            if (file.isDirectory()) {
                temp += jlsRec(file, module + "." + file.getName());
            } else if (file.isFile()) { // Si c'est un fichier '.java' on prend les informations nécessaires
                int len = file.getName().length();
                if (len > 5 && file.getName().substring(len-5).equals(".java")){
                    temp += file.getPath() + ","
                            + module + ","
                            + file.getName().substring(0,len-5)
                            + "\n";
                }
            }
        }
        return temp;
    }


    // Separate method to calculate csec once because csec already output the csv file augmented with csec values
    public static String addCSECToCSV(String csv) {
        return CSEC.csec(csv);
    }

    public static String addPMNTToCSV(String csv){
        return PMNT.pmnt(csv);
    }

    public static String addMetricsToCSV(String csv) {
        String result = "filePath,module,fileName,csec,pmnt,wmc,rfc,tpc,dc,cc\n";
        String[] csvEntries = csv.split("\n");

        for (String csvEntry : csvEntries) {
            try {
                String filePath = csvEntry.split(",")[0];
                result += csvEntry + ","
                        + WMC.wmc(filePath) + ","
                        + RFC.rfc(filePath) + ","
                        + TPC.tpc(filePath) + ","
                        + DC.dc(filePath) + ","
                        + CC.cc(filePath) + ","
                        + "\n";
            } catch (IOException | ParseException e) {
                System.out.println("Error : Parsing error while calculating metrics");
            }
        }

        return result;
    }

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

    public static void main(String[] args) {

        // path of the folder
        String folderPath = "/Users/anthony/Desktop/jfreechart-master";
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
        writeToFile(result, "output.csv");



//        // TESTING
//        try {
//            // Path of the analyzed java fileString path = "
//            String path = "/Users/anthony/Desktop/IFT3913---TP1-main/src.java";
//
//            // Method names in a SimpleName format for a clean display and easy comparison as key
//            // and their content as value
//            HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(path);
//            // Method names as key and a list of their methods calls as value
//            HashMap<SimpleName, ArrayList<SimpleName>> methodCallsNames = new HashMap<>();
//            for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
//                methodCallsNames.put(e.getKey(), ASTparserMethods.getMethodCallsNamesInsideAMethod(e.getValue()));
//            }
//
//            // Tests
//            System.out.println("Méthodes : " + methods.keySet());
//            System.out.println("Nombre de méthodes : " + methods.size());
//
//            Set<SimpleName> uniqueMethodCalls = new HashSet<>();
//            int methodCallsCount = 0;
//
//            for (Map.Entry<SimpleName, ArrayList<SimpleName>> e : methodCallsNames.entrySet()) {
//                System.out.println("Nom de la méthode : " + e.getKey() + " --- nombre de méthodes appelées : " + e.getValue().size());
//                methodCallsCount += e.getValue().size();
//                uniqueMethodCalls.add(e.getKey());
//                uniqueMethodCalls.addAll(e.getValue());
//            }
//            System.out.println("Total amount of method calls in file : " + methodCallsCount);
//            System.out.println("Total amount of uniques method calls in file : " + uniqueMethodCalls.size());
//
//            System.out.println("\nDensité de commentaires : " + DC.dc(path));
//            System.out.println("Nombre de tests : " + TPC.tpc(path));
//            System.out.println("Densité de tests : " + DT.dt(path));
//            System.out.println("RFC : " + RFC.rfc(path));
//            System.out.println("WMC : " + WMC.wmc(path));
//            System.out.println("CC : " + CC.cc(path));
//            System.out.println("CSEC : " + CSEC.csec(path));
//
//        } catch (IOException | ParseException e) {
//            System.out.println("Error");
//        }
    }
	
}

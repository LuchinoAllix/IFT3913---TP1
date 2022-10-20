package org.example;

import java.io.*;

public class CSEC {

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


//    /*
//     * Description :
//     * ----------
//     * Cette méthode est utilisée dans lcsec pour traiter de la partie
//     * récursive du problème, elle permet de calculer la métrique csec
//     *
//     * Paramètres :
//     * ----------
//     * @param csv File donné par la méthode jls
//     * @return String la chaine de caractères qui contient la même chose que le
//     * fichier csv donné en paramètre mais enrichit de la métrique csec.
//     *
//     * Informations complémentaires :
//     * ----------
//     * Ne possède d'effet de bord que s'il y a un problème quelque avec
//     * l'ouverture/lecture/fermeture d'un fichier.
//     */
//    public static String csec(String path) throws ParseException, IOException {
//        StringBuilder result = new StringBuilder();
//        ArrayList<String> files = new ArrayList<>();
//        getAllFiles(path,files);
//        System.out.println(files.toString());
//        String[][] csec = new String[files.size()][2];
//
//        for (int i = 0; i < files.size(); i++) {
//            csec[i][0] = files.get(i).split("<")[0];
//            csec[i][1] = "";
//        }
//
//        for (int x = 0; x < files.size(); x++) {
//            try {
//                File file = new File(files.get(x).split("<")[1]);
//                FileReader reader = new FileReader(file);
//                BufferedReader br = new BufferedReader(reader);
//                String line;
//                while((line = br.readLine()) != null) {
//                    for (int y = 0; y < files.size(); y++) {
//                        if ((x != y) && line.contains(csec[y][0])) {
//                            if (!csec[x][1].contains(csec[y][0])) {
//                                if (csec[x][1].equals("")) {
//                                    csec[x][1] = csec[y][0];
//                                } else {
//                                    csec[x][1] = csec[x][1] + "/" + csec[y][0];
//                                }
//                            }
//                            if(!csec[y][1].contains(csec[x][0])) {
//                                if (csec[y][1].equals("")) {
//                                    csec[y][1] = csec[x][0];
//                                } else {
//                                    csec[y][1] = csec[y][1] + "/" + csec[x][0];
//                                }
//                            }
//                        }
//                    }
//                }
//                reader.close();
//            } catch (IOException e) {
//                System.out.println(" - Error - ");
//            }
//        }
//
//        for (int z = 0; z < files.size(); z++) {
//            result.append(files.get(z)).append(",").append(csec[z][1].split("/").length).append("\n");
//        }
//        return result.toString();
//    }
//
//    public static void getAllFiles(String path, ArrayList<String> files) throws ParseException, IOException {
//        File f = new File(path);
//        if (f.isFile()) {
//            int len = f.getName().length();
//            if (len > 5 && f.getName().substring(len - 5).equals(".java")) {
//                    files.add(f.getName() + "<" + f.getPath());
//            }
//        }else{
//            for (final File file : Objects.requireNonNull(f.listFiles())){
//                if (file.isDirectory()) getAllFiles(file.getPath(),files);
//            }
//        }
//    }

}

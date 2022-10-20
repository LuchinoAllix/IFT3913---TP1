package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class CSEC {

    /*
     * Description :
     * ----------
     * Cette méthode est utilisée dans lcsec pour traiter de la partie
     * récursive du problème, elle permet de calculer la métrique csec
     *
     * Paramètres :
     * ----------
     * @param csv File donné par la méthode jls
     * @return String la chaine de caractères qui contient la même chose que le
     * fichier csv donné en paramètre mais enrichit de la métrique csec.
     *
     * Informations complémentaires :
     * ----------
     * Ne possède d'effet de bord que s'il y a un problème quelque avec
     * l'ouverture/lecture/fermeture d'un fichier.
     */
    public static String csec(String path) throws ParseException, IOException {
        String result = "";
        ArrayList<String> methodes = new ArrayList<>();
        getAllMethods(path,methodes);

        String[][] csec = new String[methodes.size()][2];

        for (int i = 0; i < methodes.size(); i++) {
            csec[i][0] = methodes.get(i).split(",")[0];
            csec[i][1] = "";
        }

        for (int x = 0; x < methodes.size(); x++) {
            try {
                File file = new File(methodes.get(x).split(",")[1]);
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                String line;
                while((line = br.readLine()) != null) {
                    for (int y = 0; y < methodes.size(); y++) {
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

        for (int z = 0; z < methodes.size(); z++) {
            result += methodes.get(z) + "," + (csec[z][1].split("/").length) + "\n";
        }
        return result;
    }

    public static void getAllMethods(String path, ArrayList<String> methods) throws ParseException, IOException {
        File f = new File(path);
        if (f.isFile()) {
            int len = f.getName().length();
            if (len > 5 && f.getName().substring(len - 5).equals(".java")) {
                ArrayList<MethodDeclaration> methodDeclaration = ASTparserMethods.parseMethodDeclarations(path);
                for(MethodDeclaration m : methodDeclaration){
                    methods.add(m.getNameAsString() + "," + f.getPath());
                }
            }
        }else{
            for (final File file : Objects.requireNonNull(f.listFiles())){
                if (file.isDirectory()) getAllMethods(file.getPath(),methods);
            }
        }
    }


}

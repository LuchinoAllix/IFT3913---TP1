package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;

import java.io.IOException;
import java.util.ArrayList;

/* S'occupe de la méthode pmnt, qui permet d'obtenir la métrique PMNT
* (Pourcentage méthodes non testées) et TPC (Test Per Class). TPC est
* légèrement modifiée. Pour un fichier donnée, c'est le nombre de fois
* que toutes les méthodes du fichier ont été appelées par une méthode
* de test. */
public class PMNT {

    /* Permet d'obtenir les métriques PMNT et TPC grâce a la 'liste'
    * des fichiers entrée en paramètre sous forme de string.
    * Pour voir la forme de la string il faut reagrder la méthode
    * jls dans Main.java. */
    public static String pmnt(String csvAsString){

        // Chaque élément du tableau est le path d'un fichier java, son module et le nom de la classe.
        String[] csvEntries = csvAsString.split("\n");
        // Liste pour contenir une liste de toutes les méthodes de tout les fichiers.
        ArrayList<ArrayList<MyMethod>> fichiersDeMethods = new ArrayList<>();

        // On ajoute dans fichierDeMethods toutes les méthodes.
        for (String csvEntry : csvEntries) {
            String path = csvEntry.split(",")[0];
            try {
                fichiersDeMethods.add(ASTparserMethods.parseMyMethods(path));
            } catch (ParseException | IOException e) {
                System.out.println("Error : Parsing error while calculating metrics");
            }
        }
        for (ArrayList<MyMethod> fichier : fichiersDeMethods){
            for (MyMethod myMethod : fichier){
                /* Pour toutes les méthodes de tout les fichiers, on obtient la liste de toutes les méthodes
                qui sont appelée par des méthodes de test */
                ArrayList<SimpleName> names = null;
                try {
                    if(myMethod.stmt != null && myMethod.isTest){
                        names = ASTparserMethods.getMethodCallsNamesInsideAMethod(myMethod.stmt);
                    }
                } catch (ParseException | IOException e){
                    System.out.println("Error : Parsing error while calculating metrics");
                }
                if (names != null){
                    /* Si la liste de toutes les méthodes appelées par des méthodes de test n'est pas vide,
                    on regarde chacune de ses méthodes, et on va modifier dans fichierdeMéthode le nombre
                    de fois qu'elle à été appelée par une méthode de test. */
                    for (SimpleName name : names){
                        for(ArrayList<MyMethod> fichier2 : fichiersDeMethods){
                            for (MyMethod myMethod2 : fichier2){
                                if (myMethod2.name.equals(name)){
                                    myMethod2.isTested++;
                                }
                            }
                        }
                    }
                }
            }
        }
        /* Pour chaque méthode on calcul PMNT et TPC */
        for (int i = 0; i < fichiersDeMethods.size(); i++) {
            int nbMethods = 0;
            int nbMethodsNonTest = 0;
            int nbTests =0;
            for (MyMethod myMethod : fichiersDeMethods.get(i)){
                nbMethods++;
                if(myMethod.isTested > 0){
                    nbMethodsNonTest++;
                    nbTests+= myMethod.isTested;
                }
            }
            if(nbMethods==0) nbMethods=1;
            // On ajoute dans le tableau des donnée les nouvelles métriques.
            csvEntries[i]+= "," + (float) nbMethodsNonTest/nbMethods * 100+ "%," + nbTests;
        }
        // On transforme le tableau en String pour pouvoir le retourner.
        StringBuilder res = new StringBuilder();
        for (String s : csvEntries){
            res.append(s).append("\n");
        }
        return res.toString();
    }

}

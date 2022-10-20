package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;

import java.io.IOException;
import java.util.ArrayList;


public class PMNT {

    public static String pmnt(String csvAsString){
        String[] csvEntries = csvAsString.split("\n");
        ArrayList<ArrayList<MyMethod>> fichiersDeMethods = new ArrayList<>();

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
                ArrayList<SimpleName> names = null;
                try {
                    if(myMethod.stmt != null){
                        names = ASTparserMethods.getMethodCallsNamesInsideAMethod(myMethod.stmt);
                    }
                } catch (ParseException | IOException e){
                    System.out.println("Error : Parsing error while calculating metrics");
                }
                if (names != null){
                    for (SimpleName name : names){
                        for(ArrayList<MyMethod> fichier2 : fichiersDeMethods){
                            for (MyMethod myMethod2 : fichier2){
                                if (myMethod2.name.equals(name)) myMethod.isTested++;
                            }
                        }
                    }
                }
            }
        }


        for (int i = 0; i < fichiersDeMethods.size(); i++) {
            int nbMethods = 0;
            int nbMethodsNonTest = 0;
            for (MyMethod myMethod : fichiersDeMethods.get(i)){
                nbMethods++;
                if(myMethod.isTested > 0) nbMethodsNonTest++;
            }
            if(nbMethods==0) nbMethods=1;
            csvEntries[i]+= ","+  (float) nbMethodsNonTest/nbMethods ;
        }
        StringBuilder res = new StringBuilder();
        for (String s : csvEntries){
            res.append(s).append("\n");
        }
        return res.toString();
    }

}

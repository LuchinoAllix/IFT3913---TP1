package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // TESTING
        try {
            // Path of the analyzed java fileString path = "
            String path = "/Users/anthony/Desktop/IFT3913---TP1-main/src.java";

            // Method names in a SimpleName format for a clean display and easy comparison as key
            // and their content as value
            HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(path);
            // Method names as key and a list of their methods calls as value
            HashMap<SimpleName, ArrayList<SimpleName>> methodCallsNames = new HashMap<>();
            for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
                methodCallsNames.put(e.getKey(), ASTparserMethods.getMethodCallsNamesInsideAMethod(e.getValue()));
            }

            // Tests
            System.out.println("Méthodes : " + methods.keySet());
            System.out.println("Nombre de méthodes : " + methods.size());

            Set<SimpleName> uniqueMethodCalls = new HashSet<>();
            int methodCallsCount = 0;

            for (Map.Entry<SimpleName, ArrayList<SimpleName>> e : methodCallsNames.entrySet()) {
                System.out.println("Nom de la méthode : " + e.getKey() + " --- nombre de méthodes appelées : " + e.getValue().size());
                methodCallsCount += e.getValue().size();
                uniqueMethodCalls.add(e.getKey());
                uniqueMethodCalls.addAll(e.getValue());
            }
            System.out.println("Total amount of method calls in file : " + methodCallsCount);
            System.out.println("Total amount of uniques method calls in file : " + uniqueMethodCalls.size());

            System.out.println("\nDensité de commentaires : " + DC.dc(path));
            System.out.println("Nombre de tests : " + TPC.tpc(path));
            System.out.println("Densité de tests : " + DT.dt(path));
            System.out.println("RFC : " + RFC.rfc(path));
            System.out.println("WMC : " + WMC.wmc(path));
            System.out.println("CC : " + CC.cc(path));

        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }
    }
	
}

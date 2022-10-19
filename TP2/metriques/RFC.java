package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.IOException;
import java.util.*;

public class RFC {

    public static int rfc(String filePath) throws ParseException, IOException {
        HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(filePath);

        HashMap<SimpleName, ArrayList<SimpleName>> methodCallsNames = new HashMap<>();
        for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
            methodCallsNames.put(e.getKey(), ASTparserMethods.getMethodCallsNamesInsideAMethod(e.getValue()));
        }

        Set<SimpleName> result = new HashSet<>();
        for (Map.Entry<SimpleName, ArrayList<SimpleName>> e : methodCallsNames.entrySet()) {
            result.add(e.getKey());
            result.addAll(e.getValue());
        }

        return result.size();
    }

    public static void main(String[] args) {
        try {
            System.out.println(rfc("/Users/anthony/Desktop/IFT3913---TP1-main/src.java"));
        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }
    }
	
}

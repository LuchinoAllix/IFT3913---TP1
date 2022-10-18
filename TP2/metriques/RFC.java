package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.IOException;
import java.util.ArrayList;

public class RFC {

    public static int rfc(String filePath) throws ParseException, IOException {
        ArrayList<SimpleName> methodNames = ASTparser.parseMethodsNames(filePath);
        ArrayList<BlockStmt> methodContents = ASTparser.parseMethodsContent(filePath);
        ArrayList<SimpleName> uniqueMethodCallsNames = ASTparser.getAllUniqueMethodCallsNames(methodContents);

        // Ajoute les méthodes de la classe aux méthodes appelées dans la classe si elles ne
        // sont pas déjà dans la liste
        for (SimpleName methodName : methodNames) {
            if (!uniqueMethodCallsNames.contains(methodName)) {
                uniqueMethodCallsNames.add(methodName);
            }
        }

        return uniqueMethodCallsNames.size();
    }
	
}

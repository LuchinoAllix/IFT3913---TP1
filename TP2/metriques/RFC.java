package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;

import java.io.IOException;
import java.util.ArrayList;

public class RFC {

    public static int rfc(String filePath) throws ParseException, IOException {
        ArrayList<SimpleName> uniqueMethodCalls = ASTparser.getAllUniqueMethodCalls(ASTparser.parseMethodsContent(filePath));
        ArrayList<SimpleName> methodNames = ASTparser.parseMethodsNames(filePath);

        for (SimpleName methodName : methodNames) {
            if (!uniqueMethodCalls.contains(methodName)) {
                uniqueMethodCalls.add(methodName);
            }
        }

        return uniqueMethodCalls.size();
    }

    public static void main(String[] args) {

        try {
            System.out.println(rfc("/Users/anthony/Desktop/IFT3913---TP1-main/src.java"));
        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }
    }
	
}

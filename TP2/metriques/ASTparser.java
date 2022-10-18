package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ASTparser {

    public static ArrayList<SimpleName> parseMethodsNames(String filePath) throws ParseException, IOException {
        // result will store all the methods names inside an Arraylist
        ArrayList<SimpleName> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Then let's parse the file in a compilation unit
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Finally the compilation unit take a visitor to parse through the code for each
        // MethodDeclaration and add their name to the result
        result = (ArrayList<SimpleName>) cu.accept(new GenericListVisitorAdapter<SimpleName, Void>() {
            @Override
            public ArrayList<SimpleName> visit(MethodDeclaration n, Void arg) {
                ArrayList<SimpleName> methods = new ArrayList<>();
                super.visit(n, arg);
                methods.add(n.getName());
                return methods;
            }
        }, null);

        return result;
    }

    public static ArrayList<BlockStmt> parseMethodsContent(String filePath) throws ParseException, IOException {
        // result will store all the methods contents inside an Arraylist
        ArrayList<BlockStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Then let's parse the file in a compilation unit
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Finally the compilation unit take a visitor to parse through the code for each
        // MethodDeclaration and add their content (without documentation) to the result
        result = (ArrayList<BlockStmt>) cu.accept(new GenericListVisitorAdapter<BlockStmt, Void>() {
            @Override
            public ArrayList<BlockStmt> visit(MethodDeclaration n, Void arg) {
                ArrayList<BlockStmt> methods = new ArrayList<>();
                super.visit(n, arg);
                if (n.getBody().isPresent()) {
                    methods.add(n.getBody().get());
                }
                return methods;
            }
        }, null);

        return result;
    }

    public static ArrayList<MethodCallExpr> parseMethodCallsInsideAMethod(BlockStmt method) throws ParseException, IOException {
        // result will store all the method calls inside our BlockStmt
        ArrayList<MethodCallExpr> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExprand add them to the result
        result = (ArrayList<MethodCallExpr>) method.accept(new GenericListVisitorAdapter<MethodCallExpr, Void>() {
            @Override
            public ArrayList<MethodCallExpr> visit(MethodCallExpr n, Void arg) {
                ArrayList<MethodCallExpr> methodCalls = new ArrayList<>();
                super.visit(n, arg);
                methodCalls.add(n);
                return methodCalls;
            }
        }, null);

        return result;
    }

    public static ArrayList<MethodCallExpr> getAllMethodCalls(ArrayList<BlockStmt> methods) throws ParseException, IOException {
        ArrayList<MethodCallExpr> methodCalls = new ArrayList<>();
        for (BlockStmt method : methods) {
            methodCalls.addAll(parseMethodCallsInsideAMethod(method));
        }
        return methodCalls;
    }

    public static ArrayList<SimpleName> getAllUniqueMethodCalls(ArrayList<BlockStmt> methods) throws ParseException, IOException {
        ArrayList<MethodCallExpr> methodCalls = getAllMethodCalls(methods);
        ArrayList<SimpleName> uniqueMethodCalls = new ArrayList<>();
        for (MethodCallExpr methodCall : methodCalls) {
            if (!uniqueMethodCalls.contains(methodCall.getName())) {
                uniqueMethodCalls.add(methodCall.getName());
            }
        }
        return uniqueMethodCalls;
    }

    public static void main(String[] args) {
        // TESTING
        try {
            // Method names in a String format
            ArrayList<SimpleName> methodNames = parseMethodsNames("/Users/anthony/Desktop/IFT3913---TP1-main/src.java");
            // Method contents in a BlockStmt format to be able to visit them again later
            ArrayList<BlockStmt> methodContents = parseMethodsContent("/Users/anthony/Desktop/IFT3913---TP1-main/src.java");

            System.out.println("Méthodes : " + methodNames.toString());
            System.out.println("Nombre de méthodes : " + methodNames.size());
            System.out.println("Nombre de méthodes appelées : " + getAllMethodCalls(methodContents).size());
            System.out.println("Nombre de méthodes uniques appelées : " + getAllUniqueMethodCalls(methodContents).size());
            System.out.println(getAllUniqueMethodCalls(methodContents));
            for (int i = 0; i < methodNames.size(); i++) {
                System.out.println("Méthode : " + methodNames.get(i)
                        + " --- Nombre de méthodes appelées : " + parseMethodCallsInsideAMethod(methodContents.get(i)).size());
                //System.out.println(methodContents.get(i).toString());
            }

        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }
    }

}

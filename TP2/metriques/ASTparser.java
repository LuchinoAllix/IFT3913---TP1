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

    public static ArrayList<SimpleName> getAllMethodCallsNames(ArrayList<BlockStmt> methods) throws ParseException, IOException {
        ArrayList<MethodCallExpr> methodCalls = new ArrayList<>();
        for (BlockStmt method : methods) {
            methodCalls.addAll(parseMethodCallsInsideAMethod(method));
        }

        ArrayList<SimpleName> methodCallsNames = new ArrayList<>();
        for (MethodCallExpr methodCall : methodCalls) {
            methodCallsNames.add(methodCall.getName());
        }

        return methodCallsNames;
    }

    public static ArrayList<SimpleName> getAllUniqueMethodCallsNames(ArrayList<BlockStmt> methods) throws ParseException, IOException {
        ArrayList<SimpleName> methodCallsNames = getAllMethodCallsNames(methods);
        ArrayList<SimpleName> uniqueMethodCallsNames = new ArrayList<>();

        for (SimpleName methodCallName : methodCallsNames) {
            if (!uniqueMethodCallsNames.contains(methodCallName)) {
                uniqueMethodCallsNames.add(methodCallName);
            }
        }

        return uniqueMethodCallsNames;
    }

    public static void main(String[] args) {
        // TESTING
        try {
            // Path of the analysed java file
            String path = "/Users/anthony/Desktop/IFT3913---TP1-main/src.java";

            // Method names in a SimpleName format for a clean display and easy comparison
            ArrayList<SimpleName> methodNames = parseMethodsNames(path);
            // Method contents in a BlockStmt format to be able to visit them again later
            ArrayList<BlockStmt> methodContents = parseMethodsContent(path);
            // Names of the methods called from the methods inside methodContents
            ArrayList<SimpleName> methodCallsNames = getAllMethodCallsNames(methodContents);
            // Names of the uniques methods called from the methods inside methodContents
            ArrayList<SimpleName> uniqueMethodCallsNames = getAllUniqueMethodCallsNames(methodContents);

            System.out.println("Méthodes : " + methodNames.toString());
            System.out.println("Nombre de méthodes : " + methodNames.size());
            System.out.println("Nombre de méthodes appelées : " + methodCallsNames.size());
            System.out.println("Nombre de méthodes uniques appelées : " + uniqueMethodCallsNames.size());
            System.out.println(getAllUniqueMethodCallsNames(methodContents));

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

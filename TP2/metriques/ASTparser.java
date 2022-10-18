package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ASTparser {

    public static HashMap<SimpleName, BlockStmt> parseMethods(String filePath) throws ParseException, IOException {
        // result will store a HashMap of the methods names (key) and their content (value)
        HashMap<SimpleName, BlockStmt> result = new HashMap<>();
        ArrayList<SimpleName> methodNames;
        ArrayList<BlockStmt> methodBodies;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Then let's parse the file in a compilation unit
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // The compilation unit take a visitor to parse through the code for each
        // MethodDeclaration and add their name to the result
        methodNames = (ArrayList<SimpleName>) cu.accept(new GenericListVisitorAdapter<SimpleName, Void>() {
            @Override
            public ArrayList<SimpleName> visit(MethodDeclaration n, Void arg) {
                ArrayList<SimpleName> methods = new ArrayList<>();
                super.visit(n, arg);
                methods.add(n.getName());
                return methods;
            }
        }, null);

        // The compilation unit take a visitor to parse through the code for each
        // MethodDeclaration and add their content (without documentation) to the result
        methodBodies = (ArrayList<BlockStmt>) cu.accept(new GenericListVisitorAdapter<BlockStmt, Void>() {
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

        for (int i = 0; i < methodNames.size(); i++) {
            result.put(methodNames.get(i), methodBodies.get(i));
        }

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

    public static ArrayList<SimpleName> parseMethodCallsNamesInsideAMethod(BlockStmt method) throws ParseException, IOException {
        ArrayList<MethodCallExpr> methodCalls = parseMethodCallsInsideAMethod(method);
        ArrayList<SimpleName> methodCallsNames = new ArrayList<>();
        for (MethodCallExpr methodCall : methodCalls) {
            methodCallsNames.add(methodCall.getName());
        }

        return methodCallsNames;
    }

    public static ArrayList<WhileStmt> parseMethodWhile(BlockStmt method) throws ParseException, IOException {
        // result will store all the method calls inside our BlockStmt
        ArrayList<WhileStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExprand add them to the result
        result = (ArrayList<WhileStmt>) method.accept(new GenericListVisitorAdapter<WhileStmt, Void>() {
            @Override
            public ArrayList<WhileStmt> visit(WhileStmt n, Void arg) {
                ArrayList<WhileStmt> methodCalls = new ArrayList<>();
                super.visit(n, arg);
                methodCalls.add(n);
                return methodCalls;
            }
        }, null);

        return result;
    }

    public static ArrayList<IfStmt> parseMethodIf(BlockStmt method) throws ParseException, IOException {
        // result will store all the method calls inside our BlockStmt
        ArrayList<IfStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExprand add them to the result
        result = (ArrayList<IfStmt>) method.accept(new GenericListVisitorAdapter<IfStmt, Void>() {
            @Override
            public ArrayList<IfStmt> visit(IfStmt n, Void arg) {
                ArrayList<IfStmt> methodCalls = new ArrayList<>();
                super.visit(n, arg);
                methodCalls.add(n);
                return methodCalls;
            }
        }, null);

        return result;
    }

    public static ArrayList<ForStmt> parseMethodFor(BlockStmt method) throws ParseException, IOException {
        // result will store all the method calls inside our BlockStmt
        ArrayList<ForStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExprand add them to the result
        result = (ArrayList<ForStmt>) method.accept(new GenericListVisitorAdapter<ForStmt, Void>() {
            @Override
            public ArrayList<ForStmt> visit(ForStmt n, Void arg) {
                ArrayList<ForStmt> methodCalls = new ArrayList<>();
                super.visit(n, arg);
                methodCalls.add(n);
                return methodCalls;
            }
        }, null);

        return result;
    }

    public static float parseDC(String filePath) throws ParseException, IOException {
        // result will store all the method calls inside our BlockStmt
        ArrayList<LineComment> lineComments;
        ArrayList<BlockComment> blockComments;
        ArrayList<JavadocComment> javadocComments;

        int comms = 0;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExprand add them to the result
        lineComments = (ArrayList<LineComment>) cu.accept(new GenericListVisitorAdapter<LineComment, Void>() {
            @Override
            public ArrayList<LineComment> visit(LineComment n, Void arg) {
                ArrayList<LineComment> lineComments = new ArrayList<>();
                super.visit(n, arg);
                lineComments.add(n.asLineComment());
                return lineComments;
            }
        }, null);

        comms += lineComments.size();

        blockComments = (ArrayList<BlockComment>) cu.accept(new GenericListVisitorAdapter<BlockComment, Void>() {
            @Override
            public ArrayList<BlockComment> visit(BlockComment n, Void arg) {
                ArrayList<BlockComment> blockComments = new ArrayList<>();
                super.visit(n, arg);
                blockComments.add(n.asBlockComment());
                return blockComments;
            }
        }, null);

        for(BlockComment blockComment : blockComments){
            comms++;
            String comment = blockComment.toString();
            for (int i = 0; i < comment.length(); i++) {
                if(comment.charAt(i) == '\n'){
                    comms++;
                }
            }
        }

        javadocComments = (ArrayList<JavadocComment>) cu.accept(new GenericListVisitorAdapter<JavadocComment, Void>() {
            @Override
            public ArrayList<JavadocComment> visit(JavadocComment n, Void arg) {
                ArrayList<JavadocComment> javadocComments = new ArrayList<>();
                super.visit(n, arg);
                javadocComments.add(n.asJavadocComment());
                return javadocComments;
            }
        }, null);

        for(JavadocComment javadocComment : javadocComments){
            comms++;
            String comment = javadocComment.toString();
            for (int i = 0; i < comment.length(); i++) {
                if(comment.charAt(i) == '\n'){
                    comms++;
                }
            }
        }

        int lines = 0;

        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(line.length() > 0) lines ++;
            }
            reader.close();

        } catch (IOException e) {
            assert true; // Pour ne rien faire
        }

        if (lines == 0) return 0; // Si le fichier est vide

        return (float )comms/lines ;
    }

    public static void main(String[] args) {
        // TESTING
        try {
            // Path of the analyzed java file
            String path = "/Users/anthony/Desktop/IFT3913---TP1-main/src.java";

            // Method names in a SimpleName format for a clean display and easy comparison as key
            // and their content as value
            HashMap<SimpleName, BlockStmt> methods = parseMethods(path);
            // Method names as key and a list of their methods calls as value
            HashMap<SimpleName, ArrayList<SimpleName>> methodCallsNames = new HashMap<>();
            for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
                methodCallsNames.put(e.getKey(), parseMethodCallsNamesInsideAMethod(e.getValue()));
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

            System.out.println("\nDensité de commentaires : " + parseDC(path));

        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }
    }

}
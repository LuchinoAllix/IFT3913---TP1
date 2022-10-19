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

import java.io.File;
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

    public static ArrayList<MethodDeclaration> parseMethodDeclarations(String filePath) throws ParseException, IOException {
        ArrayList<MethodDeclaration> methodDeclarations;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        methodDeclarations = (ArrayList<MethodDeclaration>) cu.accept(new GenericListVisitorAdapter<MethodDeclaration, Void>() {
            @Override
            public ArrayList<MethodDeclaration> visit(MethodDeclaration n, Void arg) {
                ArrayList<MethodDeclaration> methods = new ArrayList<>();
                super.visit(n, arg);
                methods.add(n);
                return methods;
            }
        }, null);

        return methodDeclarations;
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

    public static ArrayList<LineComment> parseLineComments(String filePath) throws ParseException, IOException {
        ArrayList<LineComment> lineComments;

        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        lineComments = (ArrayList<LineComment>) cu.accept(new GenericListVisitorAdapter<LineComment, Void>() {
            @Override
            public ArrayList<LineComment> visit(LineComment n, Void arg) {
                ArrayList<LineComment> lineComments = new ArrayList<>();
                super.visit(n, arg);
                lineComments.add(n.asLineComment());
                return lineComments;
            }
        }, null);

       return lineComments;
    }

    public static ArrayList<BlockComment> parseBlockComments(String filePath) throws ParseException, IOException {
        ArrayList<BlockComment> blockComments;

        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        blockComments = (ArrayList<BlockComment>) cu.accept(new GenericListVisitorAdapter<BlockComment, Void>() {
            @Override
            public ArrayList<BlockComment> visit(BlockComment n, Void arg) {
                ArrayList<BlockComment> blockComments = new ArrayList<>();
                super.visit(n, arg);
                blockComments.add(n.asBlockComment());
                return blockComments;
            }
        }, null);

        return blockComments;
    }

    public static ArrayList<JavadocComment> parseJavadocComments(String filePath) throws ParseException, IOException {
        ArrayList<JavadocComment> javadocComments;

        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        javadocComments = (ArrayList<JavadocComment>) cu.accept(new GenericListVisitorAdapter<JavadocComment, Void>() {
            @Override
            public ArrayList<JavadocComment> visit(JavadocComment n, Void arg) {
                ArrayList<JavadocComment> javadocComments = new ArrayList<>();
                super.visit(n, arg);
                javadocComments.add(n.asJavadocComment());
                return javadocComments;
            }
        }, null);

        return javadocComments;
    }

    public static ArrayList<Boolean> parseTests(String filePath) throws ParseException, IOException {
        ArrayList<Boolean> tests;
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        tests = (ArrayList<Boolean>) cu.accept(new GenericListVisitorAdapter<Boolean, Void>() {
            @Override
            public List<Boolean> visit(MethodDeclaration n, Void arg) {
                ArrayList<Boolean> tests = new ArrayList<>();
                super.visit(n, arg);
                if(! n.getAnnotationByName("Test").equals(Optional.empty())){
                    tests.add(true);
                } else {
                    tests.add(false);
                }
                return tests;
            }
        }, null);

        return tests;
    }


    public static void main(String[] args) {
        // TESTING
        try {
            // Path of the analyzed java fileString path = "
            String path = "C:\\Users\\luchi\\Documents\\Unif\\Udem\\Cours\\3 - A22\\3913\\TP\\IFT3913---TP1\\TP1\\src.java";

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

            System.out.println("\nDensité de commentaires : " + DC.dc(path));
            System.out.println("Nombre de tests : " + TPC.tpc(path));
            System.out.println("Densité de tests : " + DT.dt(path));

        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }
    }
}
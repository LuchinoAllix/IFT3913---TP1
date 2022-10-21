package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/*
* Classe regroupant des méthodes permetant d'obtenir des données sur les méthodes
* grâce à la classe javaparser (voir le README pour plus d'imformation sur ce module.
* */
public class ASTparserMethods {

    /* todo */
    public static HashMap<SimpleName, BlockStmt> parseMethods(String filePath) throws  IOException {
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

        if (methodNames.size() <= methodBodies.size()) {
            for (int i = 0; i < methodNames.size(); i++) {
                result.put(methodNames.get(i), methodBodies.get(i));
            }
        } else {
            for (int i = 0; i < methodBodies.size(); i++) {
                result.put(methodNames.get(i), methodBodies.get(i));
            }
        }

        return result;
    }

    // marquée comme jamais utilisée
    public static ArrayList<MethodDeclaration> parseMethodDeclarations(String filePath) throws IOException {
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

    /* todo */
    public static ArrayList<MethodCallExpr> getMethodCallsInsideAMethod(BlockStmt method) {
        // result will store all the method calls inside our BlockStmt
        ArrayList<MethodCallExpr> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExpand add them to the result
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

    /* todo */
    public static ArrayList<SimpleName> getMethodCallsNamesInsideAMethod(BlockStmt method) throws ParseException, IOException {
        ArrayList<MethodCallExpr> methodCalls = getMethodCallsInsideAMethod(method);
        ArrayList<SimpleName> methodCallsNames = new ArrayList<>();
        for (MethodCallExpr methodCall : methodCalls) {
            methodCallsNames.add(methodCall.getName());
        }

        return methodCallsNames;
    }

    /* Méthode permetant de savoir quelles méthodes dans un fichier sont des
    * méthodes de tests */
    public static ArrayList<Optional<AnnotationExpr>> parseTests(String filePath) throws IOException {
        ArrayList<Optional<AnnotationExpr>> tests; // valeur retournée

        // Utilisation du module javaParser
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Utilisation du module javaParser pour obtenir la liste des méthodes de test.
        tests = (ArrayList<Optional<AnnotationExpr>>) cu.accept(new GenericListVisitorAdapter<Optional<AnnotationExpr>, Void>() {
            @Override
            public ArrayList<Optional<AnnotationExpr>> visit(MethodDeclaration n, Void arg) {
                ArrayList<Optional<AnnotationExpr>> tests = new ArrayList<>();
                super.visit(n, arg);
                tests.add(n.getAnnotationByName("Test"));
                return tests;
            }
        }, null);

        return tests;
    }

    /* Methode permettant d'obtenir la liste des méthodes dans un fichier sous forme
    * MyMethode, utilisé dans PMNT. */
    public static ArrayList<MyMethod> parseMyMethods(String filePath) throws ParseException, IOException {

        // Création de listes d'attributs
        ArrayList<SimpleName> methodNames;
        ArrayList<BlockStmt> methodBodies;
        ArrayList<Boolean> tests;
        ArrayList<MyMethod> methods = new ArrayList<>();

        // Utilisation du module javaParser
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Utilisation du module javaParser pour obtenir la liste des noms de méthodes
        methodNames = (ArrayList<SimpleName>) cu.accept(new GenericListVisitorAdapter<SimpleName, Void>() {
            @Override
            public ArrayList<SimpleName> visit(MethodDeclaration n, Void arg) {
                ArrayList<SimpleName> methods = new ArrayList<>();
                super.visit(n, arg);
                methods.add(n.getName());
                return methods;
            }
        }, null);

        // Utilisation du module javaParser pour obtenir la liste des contenus des méthodes
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

        // Utilisation du module javaParser pour obtenir la liste des méthodes test
        tests = (ArrayList<Boolean>) cu.accept(new GenericListVisitorAdapter<Boolean, Void>() {
            @Override
            public ArrayList<Boolean> visit(MethodDeclaration n, Void arg) {
                ArrayList<Boolean> methods = new ArrayList<>();
                super.visit(n, arg);
                methods.add(!n.getAnnotationByName("Test").equals(Optional.empty()));
                return methods;
            }
        }, null);

        // On ajoute dans la listes des objet MyMethod a retourner grâce aux listes précédentes
        for (int i = 0; i < tests.size(); i++) {
            MyMethod m;
            if(methodBodies.size()< tests.size() && i > methodBodies.size()-1){
                m = new MyMethod(methodNames.get(i), null, tests.get(i), filePath);
            } else {
                m = new MyMethod(methodNames.get(i), methodBodies.get(i), tests.get(i), filePath);
            }
            methods.add(m);
        }
        return methods;
    }

}
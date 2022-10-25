package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ASTparserVariables {

    public static ArrayList<SimpleName> parseGlobalVariables(String filePath) throws IOException {
        ArrayList<SimpleName> result = new ArrayList<>();

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Then let's parse the file in a compilation unit
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Finally the compilation unit take a visitor to parse through a file for the
        // field declaration and add each variable declared to the result
        result = (ArrayList<SimpleName>) cu.accept(new GenericListVisitorAdapter<SimpleName, Void>() {
            @Override
            public ArrayList<SimpleName> visit(FieldDeclaration n, Void arg) {
                ArrayList<SimpleName> globalVariables = new ArrayList<>();
                super.visit(n, arg);
                NodeList<VariableDeclarator> globalVariableDeclarations = n.getVariables();
                for (VariableDeclarator variableDeclaration : globalVariableDeclarations) {
                    globalVariables.add(variableDeclaration.getName());
                }

                return globalVariables;
            }
        }, null);

        return result;
    }

    public static ArrayList<FieldAccessExpr> getFieldAccessExprInsideAMethod(BlockStmt method) {
        // result will store all the method calls inside our BlockStmt
        ArrayList<FieldAccessExpr> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // FieldAccessExpr and add them to the result
        result = (ArrayList<FieldAccessExpr>) method.accept(new GenericListVisitorAdapter<FieldAccessExpr, Void>() {
            @Override
            public ArrayList<FieldAccessExpr> visit(FieldAccessExpr n, Void arg) {
                ArrayList<FieldAccessExpr> methodFieldAccessExpr = new ArrayList<>();
                super.visit(n, arg);
                methodFieldAccessExpr.add(n);
                return methodFieldAccessExpr;
            }
        }, null);

        return result;
    }

    public static ArrayList<SimpleName> getFieldAccessExprNamesInsideAMethod(BlockStmt method) throws ParseException, IOException {
        ArrayList<SimpleName> result = new ArrayList<>();
        ArrayList<FieldAccessExpr> methodFieldAccessExpr = getFieldAccessExprInsideAMethod(method);
        for (FieldAccessExpr methodCall : methodFieldAccessExpr) {
            result.add(methodCall.getName());
        }

        return result;
    }

}

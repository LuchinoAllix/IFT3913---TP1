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
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ASTparserStmt {

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

    public static ArrayList<SwitchEntry> parseMethodSwitch(BlockStmt method) throws ParseException, IOException {
        // result will store all the method calls inside our BlockStmt
        ArrayList<SwitchEntry> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // MethodCallExprand add them to the result
        result = (ArrayList<SwitchEntry>) method.accept(new GenericListVisitorAdapter<SwitchEntry, Void>() {
            @Override
            public ArrayList<SwitchEntry> visit(SwitchEntry n, Void arg) {
                ArrayList<SwitchEntry> methodCalls = new ArrayList<>();
                super.visit(n, arg);
                methodCalls.add(n);
                return methodCalls;
            }
        }, null);

        return result;
    }

}
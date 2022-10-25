package org.example;

import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.util.ArrayList;

/* Classe contenant les méthodes permettant d'obtenir des informations sur les Statements
 * contenus dans un fichier. Réalisé grâce à Javaparser (voir le README pour plus
 * d'information sur le module) */
public class ASTparserStmt {

    /* Prends un contenu de méthode en argument et récupère la liste des Statements de type While */
    public static ArrayList<WhileStmt> getMethodWhile(BlockStmt method){
        // result will store all the method calls inside our BlockStmt
        ArrayList<WhileStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // WhileStmt add them to the result
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

    /* Prends un contenu de méthode en argument et récupère la liste des Statements de type If */
    public static ArrayList<IfStmt> getMethodIf(BlockStmt method){
        // result will store all the method calls inside our BlockStmt
        ArrayList<IfStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // IfStmt add them to the result
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

    /* Prends un contenu de méthode en argument et récupère la liste des Statements de type For */
    public static ArrayList<ForStmt> getMethodFor(BlockStmt method) {
        // result will store all the method calls inside our BlockStmt
        ArrayList<ForStmt> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // ForStmt add them to the result
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

    /* Prends un contenu de méthode en argument et récupère la liste des Statements de type Switch
    *  Inclut chaque Case d'un switch */
    public static ArrayList<SwitchEntry> getMethodSwitch(BlockStmt method){
        // result will store all the method calls inside our BlockStmt
        ArrayList<SwitchEntry> result;

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Finally the compilation unit take a visitor to parse through a method (BlockStmt) for each
        // SwitchEntry add them to the result
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
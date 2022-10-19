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

public class ASTparserComments {

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


}
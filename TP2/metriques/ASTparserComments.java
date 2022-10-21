package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.*;

/* Classe contenant les méthodes permettant d'obtenir des informations sur les commentaires
* contenus dans un fichier. Réalisé grâce à la classe javaparser (voir le README pour plus
* d'imformation sur le module. */
public class ASTparserComments {

    /* Méthode permetant d'obtenir la liste des commentaire (mono-lignes) d'un fichier. */
    public static ArrayList<LineComment> parseLineComments(String filePath) throws ParseException, IOException {
        ArrayList<LineComment> lineComments; // Valeur retournée

        // Utilisation du module javaParser
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Utilisation du module javaParser pour obtenir la liste des commentaires mono-ligne
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

    /* Méthode permetant d'obtenir la liste des commentaire (multi-lignes) d'un fichier. */
    public static ArrayList<BlockComment> parseBlockComments(String filePath) throws ParseException, IOException {
        ArrayList<BlockComment> blockComments; // Valeur retournée

        // Utilisation du module javaParser
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Utilisation du module javaParser pour obtenir la liste des commentaire multilignes
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

    /* Méthode permetant d'obtenir la liste des commentaire (javadoc) d'un fichier. */
    public static ArrayList<JavadocComment> parseJavadocComments(String filePath) throws ParseException, IOException {
        ArrayList<JavadocComment> javadocComments; // Valeur retournée

        // Utilisation du module javaParser
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        File file = new File(filePath);
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Utilisation du module javaParser pour obtenir la liste des commentaire de javadoc
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
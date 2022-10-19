package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// DC = densité commentaires = CLOC/LOC
public class DC {

    public static float dc(String filePath) throws ParseException, IOException {

        ArrayList<LineComment> lineComments = ASTparser.parseLineComments(filePath);
        ArrayList<BlockComment> blockComments =  ASTparser.parseBlockComments(filePath);
        ArrayList<JavadocComment> javadocComments = ASTparser.parseJavadocComments(filePath);
        int comments = 0;
        int lines = 0;

        comments += lineComments.size();

        for(BlockComment blockComment : blockComments){
            comments++;
            String comment = blockComment.toString();
            for (int i = 0; i < comment.length(); i++) {
                if(comment.charAt(i) == '\n') comments++;
            }
        }

        for(JavadocComment javadocComment : javadocComments){
            comments++;
            String comment = javadocComment.toString();
            for (int i = 0; i < comment.length(); i++) {
                if(comment.charAt(i) == '\n') comments++;
            }
        }

        try {
            File file = new File(filePath);
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

        return (float) comments/lines ;
    }
	
}
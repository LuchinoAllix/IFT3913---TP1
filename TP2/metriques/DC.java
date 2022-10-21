package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* Classe qui implémente la méthode dc, utilisée pour obtenir la métrique DC
* (DC = densité de commentaires) = CLOC/LOC */
public class DC {

    /* Permet de calculer la métrique dc d'un fichier donné en paramètre
    * (avec son path en String).*/
    public static float dc(String filePath) throws ParseException, IOException {

        // Listes contenant les différents types de commentaires (grâce à ASTparserComments)
        ArrayList<LineComment> lineComments = ASTparserComments.parseLineComments(filePath);
        ArrayList<BlockComment> blockComments =  ASTparserComments.parseBlockComments(filePath);
        ArrayList<JavadocComment> javadocComments = ASTparserComments.parseJavadocComments(filePath);
        // Compteurs de lignes de commentaires et de code
        int comments = 0;
        int lines = 0;

        comments += lineComments.size();

        // On compte le nombre de lignes de code de commentaire des blocks.
        for(BlockComment blockComment : blockComments){
            comments++;
            String comment = blockComment.toString();
            for (int i = 0; i < comment.length(); i++) {
                if(comment.charAt(i) == '\n') comments++;
            }
        }

        // On compte le nombre de lignes de code de commentaire de la javadoc
        for(JavadocComment javadocComment : javadocComments){
            comments++;
            String comment = javadocComment.toString();
            for (int i = 0; i < comment.length(); i++) {
                if(comment.charAt(i) == '\n') comments++;
            }
        }

        // On compte le nombre de lignes non vides (un espace ou tabulation n'est pas comptabilisée vide.
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

        if (lines == 0) return 0; // Si le fichier est vide, le ratio est 0.

        return (float) comments/lines ;
    }
	
}
package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* S'occupe de la méthode cc, qui permet d'obtenir la métrique cc (complexité cyclomatique d'un fichier java
 * en calculant le nombre de prédicats dans chaque méthode). */
public class CC {

    /* Permet d'obtenir une Map contenant les méthodes d'un fichier associé à leurs complexités
    *  cyclomatiques (nombre de prédicats dans chaque fonction, cela inclut les boucles For, While
    *  et les conditions if, switch).*/
    public static HashMap<SimpleName, Integer> cc(String filePath) throws ParseException, IOException {
        HashMap<SimpleName, Integer> result = new HashMap<>();

        // Récupère les méthodes et leurs contenu dans le fichier
        HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(filePath);

        // Pour chaque méthode compte le nombre de prédicats et ajoute le resultat dans la Map result
        for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
            int count = 1;
            ArrayList<IfStmt> methodIfStmts = ASTparserStmt.getMethodIf(e.getValue());
            count += methodIfStmts.size();
            ArrayList<ForStmt> methodForStmts = ASTparserStmt.getMethodFor(e.getValue());
            count += methodForStmts.size();
            ArrayList<WhileStmt> methodWhileStmts = ASTparserStmt.getMethodWhile(e.getValue());
            count += methodWhileStmts.size();
            ArrayList<SwitchEntry> methodSwitchStmts = ASTparserStmt.getMethodSwitch(e.getValue());
            count += methodSwitchStmts.size();

            result.put(e.getKey(), count);
        }

        return result;
    }

}

package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.IOException;
import java.util.*;

public class LCOM {

    /* Implémentation de LCOM1 */
    public static int lcom(String filePath) throws ParseException, IOException {
        int result = 0;

        // Récupère les méthodes et leurs contenu dans le fichier
        HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(filePath);
        // Récupère les variables globales
        ArrayList<SimpleName> globalVariables = ASTparserVariables.parseGlobalVariables(filePath);

        // Récupère les méthodes et leurs appels de Field à la place de leur contenu
        HashMap<SimpleName, ArrayList<SimpleName>> methodFieldCallExprNames = new HashMap<>();
        for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
            methodFieldCallExprNames.put(e.getKey(), ASTparserVariables.getFieldAccessExprNamesInsideAMethod(e.getValue()));
        }

        // Récupère les méthodes et leurs appels de Field ssi elles ne sont pas déjà présentes et
        // sont des variables globales du fichier, l'utilisation du Set assure que chaque entrée est unique
        HashMap<SimpleName, Set<SimpleName>> sortedUniqueMethodFieldCallExprNames = new HashMap<>();
        for (Map.Entry<SimpleName, ArrayList<SimpleName>> e : methodFieldCallExprNames.entrySet()) {
            Set<SimpleName> s = new HashSet<>();
            for (int i = 0; i < e.getValue().size(); i++) {
                if (globalVariables.contains(e.getValue().get(i))) {
                    s.add(e.getValue().get(i));
                }
            }
            sortedUniqueMethodFieldCallExprNames.put(e.getKey(), s);
        }

        // Pour chaque fichier le compare avec tous les autres et regarde s'ils utilisent les mêmes
        // variables globales
        Set<SimpleName> visited = new HashSet<>();

        for (Map.Entry<SimpleName, Set<SimpleName>> e1 : sortedUniqueMethodFieldCallExprNames.entrySet()) {
            visited.add(e1.getKey());

            for (Map.Entry<SimpleName, Set<SimpleName>> e2 : sortedUniqueMethodFieldCallExprNames.entrySet()) {
                Boolean isThere = false;

                if (!visited.contains(e2.getKey())) { // check si pas déjà calculé pour cette paire
                    int i = 0;
                    for (SimpleName fieldCallExpr : e2.getValue()) {
                        i++;
                        if (e1.getValue().contains(fieldCallExpr)) {
                            isThere = true;
                        }
                    }
                    if (!isThere && i == e2.getValue().size()) {
                        result++;
                    }

                }

            }

        }

        return result;
    }
}

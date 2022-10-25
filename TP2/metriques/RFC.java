package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.IOException;
import java.util.*;

/* S'occupe de la méthode rfc, qui permet d'obtenir la métrique rfc (nombre de méthodes et d'appel de
 * méthodes possibles uniques dans un fichier). */
public class RFC {

    /* Permet d'obtenir la valeur rfc qui contient le nombre de méthodes et d'appel de méthodes
    *  uniques à partir d'un fichier java*/
    public static int rfc(String filePath) throws ParseException, IOException {
        // Récupère les méthodes et leurs contenu dans le fichier
        HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(filePath);

        // Récupère les méthodes et leurs appels de méthodes à la place de leur contenu
        HashMap<SimpleName, ArrayList<SimpleName>> methodCallsNames = new HashMap<>();
        for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
            methodCallsNames.put(e.getKey(), ASTparserMethods.getMethodCallsNamesInsideAMethod(e.getValue()));
        }

        // Pour chaque méthode, ajoute son nom et tous les noms des méthodes appelées dans un Set
        // pour assurer d'ajouter uniquement les noms uniques et éviter les répétitions.
        Set<SimpleName> result = new HashSet<>();
        for (Map.Entry<SimpleName, ArrayList<SimpleName>> e : methodCallsNames.entrySet()) {
            result.add(e.getKey());
            result.addAll(e.getValue());
        }

        return result.size();
    }
	
}

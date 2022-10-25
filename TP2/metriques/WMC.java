package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* S'occupe de la méthode wmc, qui permet d'obtenir la métrique wmc (complexité d'un fichier java associé
 * à une métrique de complexité). */
public class WMC {

    /* Permet d'obtenir la valeur wmc associé à la métrique CC qui contient la somme des complexités
    *  cyclomatiques de toutes les méthodes à partir d'un fichier java*/
    public static int wmc(String filePath) throws ParseException, IOException {
        int result = 0;

        // Transforme le fichier en Map qui contient les méthodes et leurs complexités cyclomatiques
        HashMap<SimpleName, Integer> methods = CC.cc(filePath);

        // Fais la somme des complexités cyclomatiques de chaque méthode
        for (Map.Entry<SimpleName, Integer> e : methods.entrySet()) {
            result += e.getValue();
        }

        return result;
    }


	
}

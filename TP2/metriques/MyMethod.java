package org.example;

import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

/* MyMethod représente une méthode avec quelques attributs en plus.
* Ceci facilite l'utilisation de PMNT. */
public class MyMethod {

    // Attributs :
    public SimpleName name; // Nom de la méthode
    public BlockStmt stmt; // Block de la méthode (pour obtenir la liste de fonctions appelées
    public Boolean isTest; // Si la méthode est une méthode de test
    public String path; // Le chemin du fichier comportant la méthode
    public int isTested = -1; /* le nombre de fois que la méthode est appelée
    Vaut -1 par défault car plus simple pour PMNT.java */

    /* Constucteur de la classe MyMethod. Ne prend pas en compte l'attribut
    * isTesed car quand on crée l'objet on sait que la méthode n'est pas
    * testée sauf si c'est une methode de test. C'est ce a quoi sert le if. */
    public MyMethod(SimpleName name, BlockStmt stmt, Boolean isTest, String path){
        this.name = name;
        this.stmt = stmt;
        this.isTest = isTest;
        this.path = path;
        if(isTest) isTested++;
    }
}

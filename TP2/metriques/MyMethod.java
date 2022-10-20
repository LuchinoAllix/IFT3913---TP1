package org.example;

import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;

public class MyMethod {
    public SimpleName name;
    public BlockStmt stmt;
    public Boolean isTest;
    public String path;
    public int isTested = -1;
    public MyMethod(SimpleName name, BlockStmt stmt, Boolean isTest, String path){
        this.name = name;
        this.stmt = stmt;
        this.isTest = isTest;
        this.path = path;
        if(isTest) isTested++;
    }
}

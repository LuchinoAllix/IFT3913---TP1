package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CC {

    public static HashMap<SimpleName, Integer> cc(String filePath) throws ParseException, IOException {
        HashMap<SimpleName, Integer> result = new HashMap<>();
        HashMap<SimpleName, BlockStmt> methods = ASTparserMethods.parseMethods(filePath);

        for (Map.Entry<SimpleName, BlockStmt> e : methods.entrySet()) {
            int count = 1;
            ArrayList<IfStmt> methodIfStmts = ASTparserStmt.parseMethodIf(e.getValue());
            count += methodIfStmts.size();
            ArrayList<ForStmt> methodForStmts = ASTparserStmt.parseMethodFor(e.getValue());
            count += methodForStmts.size();
            ArrayList<WhileStmt> methodWhileStmts = ASTparserStmt.parseMethodWhile(e.getValue());
            count += methodWhileStmts.size();
            result.put(e.getKey(), count);
        }

        return result;
    }

}

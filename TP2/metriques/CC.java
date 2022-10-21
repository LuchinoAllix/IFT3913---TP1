package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* todo */
public class CC {

    /* todo */
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
            ArrayList<SwitchEntry> methodSwitchStmts = ASTparserStmt.parseMethodSwitch(e.getValue());
            count += methodSwitchStmts.size();

            result.put(e.getKey(), count);
        }

        return result;
    }

}

package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.AnnotationExpr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class TPC {

    public static int tpc(String filePath) throws ParseException, IOException {

        ArrayList<Optional<AnnotationExpr>> tests = ASTparserMethods.parseTests(filePath);
        int tests_cnt = 0;

        for (Optional<AnnotationExpr> test : tests)
            if(!test.equals(Optional.empty())) tests_cnt++;

        return tests_cnt;
    }
	
}

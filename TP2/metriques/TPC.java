package org.example;

import com.github.javaparser.ParseException;

import java.io.IOException;
import java.util.ArrayList;


public class TPC {

    public static int tpc(String filePath) throws ParseException, IOException {

        ArrayList<Boolean> tests = ASTparser.parseTests(filePath);
        int tests_cnt = 0;

        for (Boolean b : tests)
            if(b) tests_cnt++;

        return tests_cnt;
    }
	
}

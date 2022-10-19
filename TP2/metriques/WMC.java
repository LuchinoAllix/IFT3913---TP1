package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.SimpleName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WMC {

    public static int wmc(String filePath) throws ParseException, IOException {
        HashMap<SimpleName, Integer> methods = CC.cc(filePath);
        int result = 0;
        for (Map.Entry<SimpleName, Integer> e : methods.entrySet()) {
            result += e.getValue();
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(wmc("/Users/anthony/Desktop/IFT3913---TP1-main/src.java"));
        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }

    }
	
}

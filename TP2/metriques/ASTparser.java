package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.utils.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ASTparser {

//    public static String getFileContent(String path) {
//        StringBuilder content = new StringBuilder();
//        try {
//            File file = new File(path);
//            FileReader reader = new FileReader(file);
//            BufferedReader br = new BufferedReader(reader);
//            String line;
//            while((line = br.readLine()) != null) {
//                content.append(line);
//                content.append(System.lineSeparator());
//            }
//            reader.close();
//        } catch (IOException e) {
//            System.out.println(" - Error - ");
//        }
//        return content.toString();
//    }

    public static ArrayList<String> parseMethods() throws ParseException, IOException {
        // result will store all the methods and their content inside an Arraylist
        ArrayList<String> result = new ArrayList<>();

        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // Then let's parse the file in a compilation unit
        File file = new File("/Users/anthony/Desktop/IFT3913---TP1-main/src.java");
        CompilationUnit cu = StaticJavaParser.parse(file);

        // Finally the compilation unit take a visitor to parse through the code for each
        // MethodDeclaration and add them to the result
        result = (ArrayList<String>) cu.accept(new GenericListVisitorAdapter<String, Void>() {
            @Override
            public ArrayList<String> visit(MethodDeclaration n, Void arg) {
                ArrayList<String> arr = new ArrayList<>();
                super.visit(n, arg);
                arr.add(n.toString());
                return arr;
            }
        }, null);

        return result;
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> methods = parseMethods();
            System.out.println("Nombre de méthodes : " + methods.size());
            System.out.println(methods);
        } catch (IOException | ParseException e) {
            System.out.println("Error");
        }

    }

}

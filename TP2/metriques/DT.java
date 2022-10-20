package org.example;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class DT {

    public static int code_lines = 0;
    public static int test_lines = 0;

    public static float dt(String folderPath) throws ParseException, IOException {

        File folder = new File(folderPath);
        dt_Rec(folder);

        if(test_lines==0) return 1;

        return (float) code_lines/test_lines;
    }

    public static void dt_Rec(File path) throws IOException {
        if (path.isFile()) {
            int len = path.getName().length();
            if (len > 5 && path.getName().substring(len - 5).equals(".java")) {
                count(path);
            }
        }else{
            for (final File file : Objects.requireNonNull(path.listFiles())){
                if (file.isDirectory()) dt_Rec(file);
            }
        }

    }

    public static void count(File file)throws IOException{
        ArrayList<MethodDeclaration> methods = ASTparserMethods.parseMethodDeclarations(file.getPath());
        for (MethodDeclaration method : methods) {
            if (!method.getAnnotationByName("Test").equals(Optional.empty())) {
                String s = method.getDeclarationAsString();
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == '\n') {
                        test_lines++;
                    }
                }
            }
        }
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(line.length() > 0) code_lines++;
            }
            reader.close();

        } catch (IOException e) {
            assert true; // Pour ne rien faire
        }
    }

}

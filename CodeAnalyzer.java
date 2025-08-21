import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.*;
import java.lang.reflect.*;;

public class CodeAnalyzer {

    public static void analyzeCode(List<String> code) {
        Pattern classPattern = Pattern.compile("\\bclass\\s+([A-Za-z0-9_$]+)");
        Pattern validClassPattern = Pattern.compile("[A-Z][a-zA-Z0-9]*");

        Pattern varPattern = Pattern.compile("\\b(int|double|float|char|byte|short|long|boolean|String|HashSet<[A-Z][a-zA-Z]*>|HashMap<[A-Z][a-zA-Z]*,[A-Z][a-zA-Z]*>)\\s+([a-zA-Z0-9_$][a-zA-Z0-9_$]*)");
        Pattern camelCasePattern = Pattern.compile("[a-z]+([A-Z][a-z0-9]*)*");

        String skipPattern = ".*\\{|(if|else|for|while|switch|try|catch)\\b.*|.*:";

        boolean isClassFound = false;
        boolean isVariableFound = false;
        boolean semicolonMissing = false;

        for (int i = 0; i < code.size(); i++) {
            String line = code.get(i).trim();

            if (line.isEmpty()||line.matches("[{}\\s*]*")) {
                continue;
            }

            else{
                line = line.replaceAll("\".*\"", "\"\"");

                Matcher classMatcher = classPattern.matcher(line);
                if (classMatcher.find()) {
                    isClassFound = true;
                    String className = classMatcher.group(1);
                    if (validClassPattern.matcher(className).matches()) {
                        System.out.println(className + " is a valid class name");
                    } else {
                        System.out.println(className + " is an invalid class name");
                    }
                }

                Matcher varMatcher = varPattern.matcher(line);
                while (varMatcher.find()) {
                    isVariableFound = true;
                    String variableName = varMatcher.group(2);
                    if (camelCasePattern.matcher(variableName).matches()) {
                        System.out.println(variableName + " is a valid camelCase variable name");
                    } else {
                        System.out.println(variableName + " is an invalid camelCase variable name");
                    }
                }

                if (!line.matches(skipPattern) && !line.endsWith(";")) {
                    semicolonMissing = true;
                    System.out.println("Missing semicolon at line: " + (i + 1));
                }
            }
        }
        
        if (!isClassFound) {
            System.out.println("No class declaration found in this file.");
        }
        if (!isVariableFound) {
            System.out.println("No variable declarations found in this file.");
        }
        if (!semicolonMissing) {
            System.out.println("No missing semicolon detected.");
        }
    }
    
    public static void analyzeClass (File classFile) {
        Pattern pascalCasePattern = Pattern.compile("[A-Z][a-zA-Z0-9]*");
        String className = classFile.getName().replace(".class", "");
        try{
            Class<?> cls = Class.forName(className);  
            String simpleName = cls.getSimpleName();
            if (pascalCasePattern.matcher(simpleName).matches()) {
                System.out.println(simpleName + " is a valid PascalCase class name.");
            } else {
                System.out.println(simpleName + " is an invalid PascalCase class name.");
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load class: " + className);
        }
    }

    public static void main(String[] args) throws IOException {
        File folder = new File("code analyzer test");
        if (!folder.exists()|| !folder.isDirectory() ) {
            System.out.println("No such folder found");
            return; 
        }
        File[] files = folder.listFiles((folderName, fileName) -> fileName.endsWith(".java"));
        if (files.length == 0) {
            System.out.println("No .java files found.");
        }
        else{
            for (File file : files) {
                System.out.println("Found Java file: " + file.getName());
                analyzeCode(Files.readAllLines(file.toPath()));
                System.out.println();
            }
        }
        File[] classes = folder.listFiles((folderName, fileName) -> fileName.endsWith(".class"));
        if (classes.length == 0) {
            System.out.println("No .class files found.");
        }
        else{
            for (File classFile : classes) {
                System.out.println("Found class file: " + classFile.getName());
                analyzeClass(classFile);
                System.out.println();
            }
        }
    }
}


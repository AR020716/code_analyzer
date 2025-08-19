import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;
import java.nio.file.Files;

public class CodeAnalyzer {

    public static void ClassNameChecker(List<String> code){
        Pattern classPattern = Pattern.compile("\\bclass\\s+([A-Za-z0-9_$]+)");
        Pattern validClassPattern = Pattern.compile("[A-Z][a-zA-Z0-9]*");

        boolean classFlag = false;

        for (String line : code){
            Matcher classMatcher = classPattern.matcher(line);
            if (classMatcher.find()){
                classFlag = true;
                String className = classMatcher.group(1);
                if (validClassPattern.matcher(className).matches()){
                    System.out.println("Valid class name: " +className);
                }
                else{
                    System.out.println("Invalid class name: " +className);
                }
            }
        }
        if (!classFlag){
            System.out.println("No class declaration in file.");
        }
    }

    public static void VariableNameChecker(List<String> code) {
        HashSet<String> types = new HashSet<>(Arrays.asList(
            "int", "double", "float", "char", "byte", "short", "long", "boolean", "String", "HashSet<[A-Z][a-zA-Z]*>", "HashMap<[A-Z][a-zA-Z]*,[A-Z][a-zA-Z]*>"
        ));

        String typePattern = "\\b(" + String.join("|", types) + ")\\s+([a-zA-Z_$][a-zA-Z0-9_$]*)";
        Pattern varPattern = Pattern.compile(typePattern);
        Pattern camelCasePattern = Pattern.compile("[a-z]+([A-Z][a-z0-9]*)*");

        boolean variableFlag = false;

        for (String line : code) {
            Matcher varMatcher = varPattern.matcher(line);
            while (varMatcher.find()) {
                variableFlag = true;
                String variableName = varMatcher.group(2);
                if (camelCasePattern.matcher(variableName).matches()) {
                    System.out.println("Valid variable name: " + variableName);
                } else {
                    System.out.println("Invalid variable name: " + variableName);
                }
            }
        }

        if (!variableFlag) {
            System.out.println("No variable declaration found in this file.");
        }
    }

    public static void main(String[] args) throws IOException {
        File folder = new File("code analyzer test");
        List<File> tempList = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".java")) {
                   tempList.add(file); 
                }
            }
            File[] temp = tempList.toArray(new File[0]);
            if (!tempList.isEmpty()) {
                for (File file : temp) {
                    System.out.println("Found Java file: " + file.getName());
                    List<String> code = Files.readAllLines(file.toPath());
                    List<String> filteredCode = new ArrayList<>();
                    for (String line : code){
                        String filtered = line.replaceAll("\".*\"" , "\"\"");
                        filteredCode.add(filtered);
                    }
                    ClassNameChecker(filteredCode);
                    VariableNameChecker(filteredCode);
                    System.out.println();
                }
            } else {
                System.out.println("No .java files found.");
            }
        }
        else {
            System.out.println("No folder found.");
        }
    }
}
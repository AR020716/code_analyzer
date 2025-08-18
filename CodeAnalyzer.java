import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.*;
public class CodeAnalyzer {
    public static void ClassnameChecker(List<String> code) { 
        Pattern classPattern = Pattern.compile("\\bclass\\s+([A-Za-z0-9_$]+)");
        
        Pattern validClassPattern = Pattern.compile("[A-Z][a-zA-Z0-9]*");
        boolean isClass = false;
                for (String line : code) {
                Matcher matcher = classPattern.matcher(line);
                if (matcher.find()) {
                isClass = true;
                String className = matcher.group(1);
                if (validClassPattern.matcher(className).matches()) {
                    System.out.println(className + " is a valid class name");
                    
                } else {
                    System.out.println(className + " is an invalid class name");
                }
              
            }
         }
         if(!isClass){
            System.out.println("No class declaration found in this file ");
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
            ClassnameChecker(code);
            System.out.println();
            }
        } else {
            System.out.println("No .java files found.");
        }
        }
        else {
            System.out.println("No folder found");
        }
    }
    
}


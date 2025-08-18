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
                    ClassNameChecker(code);
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
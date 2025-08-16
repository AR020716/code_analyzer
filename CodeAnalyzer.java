import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodeAnalyzer {
    public static void main(String[] args) {
        File folder = new File("code_analyzer/code analyzer test");
        List<File> tempList = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".java")) {
                   tempList.add(file); 
                }
            }
        }
        else {
            System.out.println("No file in folder");
        }
        File[] temp = tempList.toArray(new File[0]);
        if (temp != null) {
            for (File file : temp) {
                System.out.println("Found Java file: " + file.getName());
            }
        } else {
            System.out.println("No .java files found.");
        }
    }
}
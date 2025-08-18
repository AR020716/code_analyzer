import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;

public class CodeAnalyzer {
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
                    for (String line : code){
                        System.out.println(line);
                    } 

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
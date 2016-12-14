package concurrent_counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kv on 11.12.16.
 *
 * Класс создает текстовые файлы и заполняет их случайными числами типа int
 *
 */
public class CaseGenerator {

    static List<File> files;
    PrintWriter out;
    int qty;
    ArrayList<String> paths;

    public CaseGenerator(){}

    {
        files = new ArrayList<>();
        paths = new ArrayList<>();
        qty = 100;
        generateFiles(qty);
        fillFile(files);
        fillPaths();
    }

    private void generateFiles(int quantity){
        for (int i = 0; i < quantity; i++) {
            files.add(new File("file" + i + ".txt"));
        }
    }

    public int generateRandomInt(){
        Random random = new Random();
        int x = random.nextInt();
        return x;
    }

    public void fillFile(List<File> files){
        for (int i = 0; i < files.size(); i++) {
            try {
                out = new PrintWriter(files.get(i));
                for (int j = 0; j < 1000; j++) {
                    out.println(generateRandomInt());
                }
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillPaths(){
        for (int i = 0; i < files.size(); i++) {
            paths.add(files.get(i).getAbsolutePath());
        }
    }

    String getFilePath(int i){
        return paths.get(i);
    }
}

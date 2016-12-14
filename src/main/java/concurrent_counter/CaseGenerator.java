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
class CaseGenerator {

    private static List<File> files;
    private ArrayList<String> paths;

    CaseGenerator(int qty) {
        files = new ArrayList<>();
        paths = new ArrayList<>();
        generateFiles(qty);
        fillFile(files);
        fillPaths();
    }

    private void generateFiles(int quantity){
        for (int i = 0; i < quantity; i++) {
            files.add(new File("file" + i + ".txt"));
        }
    }

    private int generateRandomInt(){
        return new Random().nextInt();
    }

    private void fillFile(List<File> files){
        for (File file : files) {
            try {
                PrintWriter out = new PrintWriter(file);
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
        for (File file : files) {
            paths.add(file.getAbsolutePath());
        }
    }

    String getFilePath(int i){
        return paths.get(i);
    }
}

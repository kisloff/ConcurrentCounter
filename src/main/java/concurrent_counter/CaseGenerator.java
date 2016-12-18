package concurrent_counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static Logger logger = LoggerFactory.getLogger(concurrent_counter.CaseGenerator.class);
    private List<File> files;
    private ArrayList<String> paths;

    CaseGenerator(int filesQty, int intQty) {
        files = new ArrayList<>();
        paths = new ArrayList<>();
        generateFiles(filesQty);
        fillFile(files, intQty);
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

    private void fillFile(List<File> files, int intQty){
        for (File file : files) {
            try {
                PrintWriter out = new PrintWriter(file);
                for (int j = 0; j < intQty; j++) {
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

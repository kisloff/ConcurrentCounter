package concurrent_counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kv on 10.12.16.
 *
 * Класс запускает создание файлов, заполнение их случайными числами
 * и производит вывод в консоль суммы положительных четных чисел из файлов.
 * Считывание происходит параллельно.
 *
 */

public class CounterMain {

    public static Logger logger = LoggerFactory.getLogger(CounterMain.class);

    public void setFilesQuantity(int filesQuantity) {
        this.filesQuantity = filesQuantity;
    }

    public void setNumsQuantity(int numsQuantity) {
        this.numsQuantity = numsQuantity;
    }

    private int filesQuantity;
    private int numsQuantity;

    public int getFilesQuantity() {
        return filesQuantity;
    }

    public int getNumsQuantity() {
        return numsQuantity;
    }

    public static void main(String[] args) {

        CounterMain cm = new CounterMain();

        logger.warn("concurrent_counter CounterMain ");

        Scanner userInput = new Scanner(System.in);

        System.out.println("Choose source:");
        System.out.println("Input '1' for generate random numbers");
        System.out.println("Input '2' to use files from directory");

        String menuItem;
        if (userInput.hasNext()){
            menuItem = userInput.nextLine();
            switch (menuItem){
                case "1":
                    System.out.println("input files quantity");
                    int filesQty = userInput.nextInt();
                    cm.setFilesQuantity(filesQty);

                    System.out.println("input numbers quantity");
                    int numbersQty = userInput.nextInt();
                    cm.setNumsQuantity(numbersQty);

                    cm.useCaseGenerator(cm.getFilesQuantity(), cm.getNumsQuantity());

                    break;
                case "2":
                    String direcroryPath = "/Users/kv/Documents/Innopolis/projects/Homeworks/ConcurrentCounter/src/main/resources/textfiles";
                    cm.useDirectory(direcroryPath);
                    break;
                default:
                    System.out.println("Выбрана неверная команда");
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void useCaseGenerator(int fileQty, int numQty){
        CaseGenerator cg = new CaseGenerator(fileQty, numQty);

        for (int i = 0; i < fileQty; i++) {
            new Thread(new ReaderThread(cg.getFilePath(i))).start();
        }
    }

    private void useDirectory(String directoryPath){
        List<String> pathList = new ArrayList();

        pathList = findAllFiles(new File(directoryPath));

        for (int i = 0; i < pathList.size(); i++) {
            new Thread(new ReaderThread(pathList.get(i))).start();
        }
    }

    private List findAllFiles(File dir){
        List<String> arrayList = new ArrayList<>();
        File[] files = dir.listFiles();
        if(files != null){
            for(File child: files){
                if(child.isDirectory()){
                    findAllFiles(new File(child.getPath()));
                } else if(child.isFile()){
                    arrayList.add(child.getAbsolutePath());
                }
            }
        }
        return arrayList;
    }
}

package concurrent_counter;

import com.sun.deploy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.Runtime.getRuntime;

/**
 * Created by kv on 10.12.16.
 *
 * Класс создает поток, инкрементирующий разделяемую переменную
 *
 */
public class ReaderThread implements Runnable{

    private static BigInteger bigInteger;
    private static final Object monitor;
    private String filePath;
    static volatile boolean isInputValid = true;
    public static Logger logger = LoggerFactory.getLogger(concurrent_counter.ReaderThread.class);

    static {
        monitor = new Object();
    }

    ReaderThread(String path){
        bigInteger = BigInteger.ZERO;
        filePath = path;
    }

    @Override
    public void run() {

       // while (isInputValid) {
            synchronized (monitor) {
                bigInteger = bigInteger.add(getResource());

                System.out.print(Thread.currentThread().getName() + " ");
                System.out.printf("%, d", bigInteger);
                System.out.println();

                try {
                    synchronized (monitor) {
                        monitor.wait(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        //}
    }

    private BigInteger getResource(){
        Scanner scanner;
        int nextInt;
        String nextString;
        BigInteger sum = BigInteger.ZERO;
        try {
            scanner = new Scanner(new File(filePath));
            while(scanner.hasNext()){
                //nextInt = scanner.nextInt();
                nextString = scanner.nextLine();
                if(nextString.matches("-?\\d+(\\.\\d+)?")){
                    nextInt = Integer.parseInt(nextString);
                    if((nextInt > 0) && (nextInt & 1) == 0) {
                        sum = sum.add(BigInteger.valueOf(nextInt));
                    }
                } else {
                    isInputValid = false;
                    Runtime runtime = getRuntime();
                    try {
                        Process process = runtime.exec("clear");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Неверный формат данных");

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            isInputValid = false;
        }

        return sum;
    }


    private void validateInput(){

    }
}

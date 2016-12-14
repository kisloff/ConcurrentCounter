package concurrent_counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

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

    static {
        monitor = new Object();
    }

    ReaderThread(String path){
        bigInteger = BigInteger.ZERO;
        filePath = path;
    }

    @Override
    public void run() {

        synchronized (monitor) {
            bigInteger = bigInteger.add(getResource());

            System.out.print(Thread.currentThread().getName() + " ");
            System.out.printf( "%, d", bigInteger);
            System.out.println();

            try {
                synchronized (monitor) {
                    monitor.wait(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private BigInteger getResource(){
        Scanner scanner;
        int nextInt;
        BigInteger sum = BigInteger.ZERO;
        try {
            scanner = new Scanner(new File(filePath));
            while(scanner.hasNextInt()){
                nextInt = scanner.nextInt();
                if((nextInt > 0) && (nextInt & 1) == 0){
                    sum = sum.add(BigInteger.valueOf(nextInt));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sum;
    }
}

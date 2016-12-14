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

    static BigInteger bigInteger;
    static final Object monitor = new Object();;
    String name;
    static int id;
    String filePath;

    ReaderThread(String path){
        id++;
        bigInteger = BigInteger.ZERO;
        this.name = "Thread " + id;
        filePath = path;
    }

    @Override
    public void run() {

        synchronized (monitor) {
            bigInteger = bigInteger.add(getResource());

            System.out.print(name + " ");
            System.out.printf( "%, d", bigInteger);
            System.out.println();

            try {
                synchronized (this.monitor) {
                    monitor.wait(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private BigInteger getResource(){
        Scanner scanner = null;
        int tmp1;
        BigInteger tmp2 = BigInteger.ZERO;
        try {
            scanner = new Scanner(new File(filePath));
            while(scanner.hasNextInt()){
                tmp1 = scanner.nextInt();
                if((tmp1 > 0) && (tmp1 % 2 == 0)){
                    tmp2 = tmp2.add(BigInteger.valueOf(tmp1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tmp2;
    }
}

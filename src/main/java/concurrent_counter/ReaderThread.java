package concurrent_counter;

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
    private Scanner scanner;
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

        checkResourceAvailable();

        int toIncrement = 0;

        while(scanner.hasNextInt() /*&& isInputValid*/){
            toIncrement = scanner.nextInt();
            if(validInt(toIncrement)) {
                synchronized (monitor) {
                    bigInteger = bigInteger.add(BigInteger.valueOf(toIncrement));
                    logger.info(Thread.currentThread().getName() + " generated int " + toIncrement + " new sum is "+ bigInteger);

                    try {
                        monitor.wait(1000);
                    } catch (InterruptedException e) {
                        logger.warn(e.getMessage());
                    }
                }
            } else{
                ;
            }
        }
    }

    private void checkResourceAvailable(){
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
        }
    }

    private boolean validInt(int toIncrement){
        if((toIncrement > 0) && (toIncrement & 1) == 0){
            return true;
        } else {
            return false;
        }
    }
}

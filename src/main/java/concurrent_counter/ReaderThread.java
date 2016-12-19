package concurrent_counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class ReaderThread implements Runnable {

    private static volatile BigInteger bigInteger;
    private static final Object monitor;
    private String filePath;
    private Scanner scanner;
    static volatile boolean isInputValid = true;
    InputValidator iv = new InputValidatorPositiveInt();
    //InputValidator iv = new InputValidatorNegativeInt();
    public static Logger logger = LoggerFactory.getLogger(concurrent_counter.ReaderThread.class);

    static {
        monitor = new Object();
    }

    ReaderThread(String path) {
        bigInteger = BigInteger.ZERO;
        filePath = path;
    }

    @Override
    public void run() {

        checkResourceAvailable(filePath);
        int toIncrement = 0;

        while (isInputValid) {

            if (!scanner.hasNextInt()) {
                isInputValid = false;
                logger.warn("not valid input " + scanner.nextLine());
            };

            while (scanner.hasNextInt() && isInputValid) {

                toIncrement = scanner.nextInt();
                if (iv.validInput(toIncrement)) {
                    synchronized (monitor) {
                        bigInteger = bigInteger.add(BigInteger.valueOf(toIncrement));
                        logger.info(Thread.currentThread().getName() + " generated int " + toIncrement + " new sum is " + bigInteger);
                    }
                } else {
                    //isInputValid = false;
                    //logger.warn("not valid input " + scanner.nextLine());
                }
            }
        }
    }

    private void checkResourceAvailable(String filePath) {
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
        }
    }
}

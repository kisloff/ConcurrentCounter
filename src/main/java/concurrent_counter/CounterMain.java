package concurrent_counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static int filesQuantity = 100;


    public static void main(String[] args) {

        logger.warn("concurrent_counter CounterMain ");

        CaseGenerator cg = new CaseGenerator(filesQuantity, 1000);

        for (int i = 0; i < filesQuantity; i++) {
            new Thread(new ReaderThread(CaseGenerator.getFilePath(i))).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

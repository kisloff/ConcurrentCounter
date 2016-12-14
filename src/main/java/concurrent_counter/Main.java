package concurrent_counter;

/**
 * Created by kv on 10.12.16.
 *
 * Класс запускает создание файлов, заполнение их случайными числами
 * и производит вывод в консоль суммы положительных четных чисел из файлов.
 * Считывание происходит параллельно.
 *
 */
public class Main {

    private final static int filesQuantity = 100;

    public static void main(String[] args) {
        CaseGenerator cg = new CaseGenerator(filesQuantity);

        for (int i = 0; i < filesQuantity; i++) {
            new Thread(new ReaderThread(cg.getFilePath(i))).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

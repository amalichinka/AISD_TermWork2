import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        int[] arr = new int[10000];

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("TestFile2.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int number = Integer.parseInt(line);
                arr[count] = number;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // добавляем числа в кучу
        Fibbo obj = Fibbo.create_heap();
        double[] workingTime = new double[10000];
        for (int i = 0; i < 10000; i++) {
            double start = System.nanoTime();
            obj.insert(arr[i]);
            double finish = System.nanoTime();
            workingTime[i] = (finish - start);
        }
        System.out.println("Среднее количество операций для добавления: " + obj.getTotalOperationsInsert());

        System.out.println("Массив workingTime:");
        for (double num : workingTime) {
            System.out.print(num + " ");
        }

        double sum1 = 0;
        for (int i = 0; i < 10000; i++) {
            sum1 += workingTime[i];
        }
        double average1 = sum1 / 10000;
        System.out.println();
        System.out.println("Среднее время добавления: " + average1);

        double start1 = System.nanoTime();
        System.out.println("Минимальное значение : " + obj.find_min());
        double finish1 = System.nanoTime();
        System.out.println("Время поиска минимального значения: " + (finish1-start1));


        System.out.println("Удаленные значения : ");
        double[] deleteTime = new double[1001];
        for(int i = 0; i<=1000; i++) {
            double start = System.nanoTime();
            System.out.print(obj.extractMin() + " ");
            double finish = System.nanoTime();
            deleteTime[i] = (finish - start);
        }
        System.out.println();

        System.out.println("Среднее количество операций для удаления : " + obj.getTotalOperationsDelete());

        System.out.println("Массив deleteTime:");
        for (double num : deleteTime) {
            System.out.print(num + " ");
        }

        double sum2 = 0;
        for (int i = 0; i <= 1000; i++) {
            sum2 += deleteTime[i];
        }
        System.out.println();
        double average2 = sum2 / 1000;
        System.out.println("Среднее время удаления " + average2);
    }
}
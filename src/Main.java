import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threadList = new ArrayList<>();//создана пустая коллекция

        String[] texts = new String[25];//массив строк  в размере 25 строк
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);// заполняем массив
        }

        long startTs = System.currentTimeMillis(); // start time

        for (String text : texts) {//проходимся циклом по тексту
            threadList.add(getNewThread(text));//и добавляем данные в коллекцию
        }

        for (Thread thread : threadList) {
            thread.start();// запускаем поток
        }

        for (Thread thread : threadList) {
            thread.join();//ожидаем завершения потока

        }
        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");// вывод времени выполнения программы
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static Thread getNewThread(String text) {//метод создания и заполнеиния потока данными
        return new Thread(
                () -> {
                    int maxSize = 0;
                    for (int i = 0; i < text.length(); i++) {
                        for (int j = 0; j < text.length(); j++) {
                            if (i >= j) {
                                continue;
                            }
                            boolean bFound = false;
                            for (int k = i; k < j; k++) {
                                if (text.charAt(k) == 'b') {
                                    bFound = true;
                                    break;
                                }
                            }
                            if (!bFound && maxSize < j - i) {
                                maxSize = j - i;
                            }
                        }
                    }
                    System.out.println(text.substring(0, 100) + " -> " + maxSize);
                });
    }

}
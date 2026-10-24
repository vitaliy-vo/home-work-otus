package utils;

import java.util.*;
import java.util.stream.Collectors;

public class WordCounter {

    /**
     * Считает количество вхождений каждого слова в массиве.
     */
    public static Map<String, Integer> countWords(String[] words) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }

    /**
     * Возвращает список уникальных слов из массива.
     */
    public static List<String> getUniqueWords(String[] words) {
        return Arrays.stream(words)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Выводит результат подсчёта слов в консоль.
     */
    public static void printResults(Map<String, Integer> wordCount) {
        System.out.println("=== Уникальные слова ===");
        getUniqueWords(wordCount.keySet().toArray(new String[0])).forEach(System.out::println);

        System.out.println("\n=== Частота слов ===");
        wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry ->
                        System.out.printf("Слово: %-20s | количество: %d%n", entry.getKey(), entry.getValue())
                );
    }


}

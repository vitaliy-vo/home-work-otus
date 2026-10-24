package utils;

import java.util.HashMap;
import java.util.Set;

public class WordCounter {

    /*-Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
            -Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
    Посчитать, сколько раз встречается каждое слово.*/

    public static void main(String[] args) {
        // 1. Создаём массив слов (10–20 элементов, есть дубликаты)
        String[] words = {
                "яблоко", "банан", "яблоко", "груша", "банан", "апельсин",
                "груша", "яблоко", "манго", "банан", "вишня",
                "яблоко", "слива", "груша", "абрикос", "яблоко",
                "персик", "банан", "ананас"
        };

       HashMap<String,Integer> wordCount =new HashMap<>();
       for (String s :words){
           wordCount.put(s,wordCount.getOrDefault(s,0)+1);
       }
        Set<String> keys = wordCount.keySet();
        System.out.println("Список уникальный слов");
        System.out.println(keys);

        wordCount.forEach((k, v) ->
                System.out.printf("Слово: %-20s | количество: %d%n", k, v)
        );
    }
}

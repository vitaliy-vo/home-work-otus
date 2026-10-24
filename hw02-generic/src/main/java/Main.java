import model.User;
import utils.ArrayUtils;

import java.util.List;
import java.util.Map;

import static utils.ArrayUtils.safeSwap;
import static utils.ArrayUtils.swap;
import static utils.WordCounter.countWords;
import static utils.WordCounter.printResults;

public class Main {
    static void main() {

        /*-Написать метод, который меняет два
        элемента массива местами (массив может быть любого ссылочного типа);*/

            String[] strings = {"A", "B", "C", "D"};
            System.out.println("До: " + java.util.Arrays.toString(strings));

            swap(strings, 1, 3);
            System.out.println("После swap(1,3): " + java.util.Arrays.toString(strings));

            Integer[] numbers = {10, 20, 30};
            safeSwap(numbers, 0, 2);
            System.out.println("После safeSwap(0,2): " + java.util.Arrays.toString(numbers));

            // Пример с объектами (можно подставить, например, криптографические объекты или DTO)
            User[] data = {
                    new User("user-1", "user-login-1"),
                    new User("user-2", "user-login-2")
            };
            swap(data, 0, 1);
            System.out.println("После обмена MyData: " + data[0].getLogin() + " <-> " + data[1].getLogin());

         /*-Написать метод, который преобразует массив в ArrayList;*/
        List<String> stringList= ArrayUtils.toList(strings);
        System.out.println(stringList);
        List<Integer> integerList=ArrayUtils.toList(numbers);
        System.out.println(integerList);

    /*    -Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
        -Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
                Посчитать, сколько раз встречается каждое слово.*/
        String[] words = {
                "чай", "кофе", "чай", "какао", "кофе", "чай",
                "бутерброд", "печенье", "бутерброд", "йогурт",
                "печенье", "чай", "какао", "йогурт", "печенье",
                "кофе", "бутерброд", "какао", "йогурт", "печенье"
        };


        Map<String, Integer> wordCount = countWords(words);
        printResults(wordCount);
    }
}

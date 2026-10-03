package ru.otus.home.work;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class HelloOtus {

    public static void main(String... args) {
        Table<String, String, Double> matrix = HashBasedTable.create();
        matrix.put("Склад 1", "Ноутбуки", 15.0);
        matrix.put("Склад 1", "Смартфоны", 42.0);
        matrix.put("Склад 2", "Ноутбуки", 8.0);

        System.out.println("Количество ноутбуков на Складе 1: " + matrix.get("Склад 1", "Ноутбуки"));
        System.out.println("Все остатки Склада 1: " + matrix.row("Склад 1"));
    }
}

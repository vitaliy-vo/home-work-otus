package atm;

import atm.exception.AtmException;
import atm.exception.CannotDispenseAmountException;
import atm.model.Banknote;
import atm.strategy.GreedyDispensingAlgorithm;

public class Main {
    public static void main(String[] args) {

        Atm atm = new Atm(new GreedyDispensingAlgorithm());

        //  Загружаем кассеты
        atm.loadCassette(new Banknote(5000), 100, 10);  // 10 купюр по 5000
        atm.loadCassette(new Banknote(1000), 200, 20);  // 20 купюр по 1000
        atm.loadCassette(new Banknote(100),  500, 50);  // 50 купюр по 100

        atm.printStatus();

        // Клиент вносит купюры
        System.out.println("\nКлиент вносит 5 купюр по 1000...");
        atm.acceptBanknotes(new Banknote(1000), 5);

        // Успешная выдача
        System.out.println("\nПопытка выдать 17300...");
        try {
            atm.withdraw(17300);  // 3x5000 + 2x1000 + 3x100
        } catch (AtmException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        atm.printStatus();

        //  Ошибка: нечем выдать остаток
        System.out.println("\nПопытка выдать 50 (нет купюр такого номинала)...");
        try {
            atm.withdraw(50);
        } catch (CannotDispenseAmountException e) {
            System.out.println("Бизнес-ошибка: " + e.getMessage());
            System.out.println("  → Запрошено: " + e.getRequestedAmount());
            System.out.println("  → Остаток:   " + e.getRemainder());
        } catch (AtmException e) {
            System.out.println("Другая ошибка ATM: " + e.getMessage());
        }

        // Ошибка: нет ячейки для номинала
        System.out.println("\nПопытка внести купюры 50 руб. (нет такой ячейки)...");
        try {
            atm.acceptBanknotes(new Banknote(50), 10);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
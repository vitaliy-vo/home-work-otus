package atm.model;

import atm.exception.CassetteOverflowException;
import atm.exception.InsufficientFundsInCassetteException;

public class Cassette {
    private final Banknote denomination;
    private final int capacity;
    private int currentCount;

    public Cassette(Banknote denomination, int capacity, int initialCount) {
        if (initialCount < 0) {
            throw new IllegalArgumentException("Начальное количество не может быть отрицательным");
        }
        if (initialCount > capacity) {
            throw new CassetteOverflowException(capacity, initialCount);
        }
        this.denomination = denomination;
        this.capacity = capacity;
        this.currentCount = initialCount;
    }

    public void put(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        if (currentCount + count > capacity) {
            throw new CassetteOverflowException(capacity, count);
        }
        currentCount += count;
    }

    public void take(int count) {
        if (count > currentCount) {
            throw new InsufficientFundsInCassetteException(
                    String.format("В ячейке %s не хватает банкнот. Запрошено: %d, есть: %d",
                            denomination, count, currentCount));
        }
        currentCount -= count;
    }

    public Banknote getDenomination() {
        return denomination;
    }

    public int getAvailableCount() {
        return currentCount;
    }

    public int getAvailableAmount() {
        return denomination.getDenomination() * currentCount;
    }
}
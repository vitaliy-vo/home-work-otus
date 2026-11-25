package atm;

import atm.exception.InvalidDenominationException;
import atm.model.Banknote;
import atm.model.Cassette;
import atm.strategy.DispensingAlgorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class Atm {
    private final Map<Banknote, Cassette> cassettes;
    private final DispensingAlgorithm dispensingAlgorithm;

    public Atm(DispensingAlgorithm dispensingAlgorithm) {
        this.cassettes = new LinkedHashMap<>();
        this.dispensingAlgorithm = dispensingAlgorithm;
    }

    public void loadCassette(Banknote banknote, int capacity, int initialCount) {
        if (cassettes.containsKey(banknote)) {
            throw new IllegalStateException("Ячейка для номинала " + banknote + " уже существует");
        }
        cassettes.put(banknote, new Cassette(banknote, capacity, initialCount));
    }

    public void acceptBanknotes(Banknote banknote, int count) {
        Cassette cassette = cassettes.get(banknote);
        if (cassette == null) {
            throw new IllegalStateException("Нет ячейки для банкноты: " + banknote);
        }
        cassette.put(count);
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }

        Map<Banknote, Integer> dispensationPlan = dispensingAlgorithm.calculate(amount, cassettes);

        for (Map.Entry<Banknote, Integer> entry : dispensationPlan.entrySet()) {
            Cassette cassette = cassettes.get(entry.getKey());
            cassette.take(entry.getValue());
        }

        System.out.println("Выдано: " + formatDispensation(dispensationPlan));
    }

    public int getTotalBalance() {
        return cassettes.values().stream()
                .mapToInt(Cassette::getAvailableAmount)
                .sum();
    }

    public void printStatus() {
        System.out.println("--- Состояние банкомата ---");
        for (Cassette cassette : cassettes.values()) {
            System.out.printf("Номинал %5d: %d шт. (на сумму %d)%n",
                    cassette.getDenomination().getDenomination(),
                    cassette.getAvailableCount(),
                    cassette.getAvailableAmount());
        }
        System.out.println("ОБЩИЙ БАЛАНС: " + getTotalBalance());
        System.out.println("---------------------------");
    }

    private String formatDispensation(Map<Banknote, Integer> plan) {
        StringBuilder sb = new StringBuilder();
        plan.forEach((banknote, count) ->
                sb.append(count).append("x").append(banknote.getDenomination()).append(" "));
        return sb.toString().trim();
    }
}
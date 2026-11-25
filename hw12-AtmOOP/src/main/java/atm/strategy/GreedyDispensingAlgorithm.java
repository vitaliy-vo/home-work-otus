package atm.strategy;

import atm.exception.CannotDispenseAmountException;
import atm.model.Banknote;
import atm.model.Cassette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreedyDispensingAlgorithm implements DispensingAlgorithm {

    @Override
    public Map<Banknote, Integer> calculate(int amount, Map<Banknote, Cassette> cassettes) {
        Map<Banknote, Integer> result = new HashMap<>();
        int remainingAmount = amount;

        // Сортируем кассеты по убыванию номинала — жадный подход
        List<Cassette> sortedCassettes = new ArrayList<>(cassettes.values());
        sortedCassettes.sort((c1, c2) -> Integer.compare(
                c2.getDenomination().getDenomination(),
                c1.getDenomination().getDenomination()
        ));

        for (Cassette cassette : sortedCassettes) {
            if (remainingAmount <= 0) break;

            int denominationValue = cassette.getDenomination().getDenomination();
            int needed = remainingAmount / denominationValue;
            int available = cassette.getAvailableCount();
            int toTake = Math.min(needed, available);

            if (toTake > 0) {
                result.put(cassette.getDenomination(), toTake);
                remainingAmount -= (toTake * denominationValue);
            }
        }

        if (remainingAmount != 0) {
            throw new CannotDispenseAmountException(amount, remainingAmount);
        }

        return result;
    }
}
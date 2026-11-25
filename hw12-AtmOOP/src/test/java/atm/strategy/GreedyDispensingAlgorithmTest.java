package atm.strategy;

import atm.exception.CannotDispenseAmountException;
import atm.model.Banknote;
import atm.model.Cassette;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GreedyDispensingAlgorithmTest {

    private final GreedyDispensingAlgorithm algorithm = new GreedyDispensingAlgorithm();

    private Map<Banknote, Cassette> createCassettes(int... denominationsAndCounts) {
        // denominationsAndCounts: [denom1, count1, denom2, count2, ...]
        var map = new java.util.LinkedHashMap<Banknote, Cassette>();
        for (int i = 0; i < denominationsAndCounts.length; i += 2) {
            int denom = denominationsAndCounts[i];
            int count = denominationsAndCounts[i + 1];
            map.put(new Banknote(denom), new Cassette(new Banknote(denom), 1000, count));
        }
        return map;
    }

    @Test
    void calculate_shouldDispenseExactAmount() {
        var cassettes = createCassettes(5000, 5, 1000, 10, 100, 20);
        Map<Banknote, Integer> result = algorithm.calculate(17000, cassettes);

        assertEquals(3, result.get(new Banknote(5000))); // 3 * 5000 = 15000
        assertEquals(2, result.get(new Banknote(1000)));  // 2 * 1000 = 2000
        assertEquals(0, result.getOrDefault(new Banknote(100), 0));
    }

    @Test
    void calculate_shouldUseLargestDenominationsFirst() {
        var cassettes = createCassettes(5000, 10, 100, 50);
        Map<Banknote, Integer> result = algorithm.calculate(5100, cassettes);

        assertEquals(1, result.get(new Banknote(5000)));
        assertEquals(1, result.get(new Banknote(100)));
    }

    @Test
    void calculate_shouldThrowWhenCannotDispenseExactAmount() {
        var cassettes = createCassettes(5000, 5);
        assertThrows(CannotDispenseAmountException.class,
                () -> algorithm.calculate(5050, cassettes));
    }

    @Test
    void calculate_shouldThrowWhenAmountExceedsTotal() {
        var cassettes = createCassettes(1000, 5); // всего 5000
        assertThrows(CannotDispenseAmountException.class,
                () -> algorithm.calculate(6000, cassettes));
    }

    @Test
    void calculate_shouldReturnEmptyMapForZeroAmount() {
        var cassettes = createCassettes(1000, 10);
        Map<Banknote, Integer> result = algorithm.calculate(0, cassettes);
        assertTrue(result.isEmpty());
    }

    @Test
    void calculate_shouldUseOnlyAvailableCassettes() {
        var cassettes = createCassettes(5000, 2, 1000, 3);
        // 2 * 5000 + 3 * 1000 = 13000
        Map<Banknote, Integer> result = algorithm.calculate(13000, cassettes);

        assertEquals(2, result.get(new Banknote(5000)));
        assertEquals(3, result.get(new Banknote(1000)));
    }

    @Test
    void calculate_shouldNotExceedCassetteCapacity() {
        var cassettes = createCassettes(1000, 5);
        Map<Banknote, Integer> result = algorithm.calculate(5000, cassettes);

        assertEquals(5, result.get(new Banknote(1000)));
    }

    @Test
    void calculate_shouldThrowWhenNoCassettes() {
        Map<Banknote, Cassette> cassettes = Map.of();
        assertThrows(CannotDispenseAmountException.class,
                () -> algorithm.calculate(1000, cassettes));
    }

    @Test
    void calculate_shouldDispenseWithSingleDenomination() {
        var cassettes = createCassettes(500, 20);
        Map<Banknote, Integer> result = algorithm.calculate(10000, cassettes);

        assertEquals(20, result.get(new Banknote(500)));
    }

    @Test
    void calculate_shouldDispenseMultipleDenominationsInOrder() {
        var cassettes = createCassettes(5000, 10, 1000, 10, 500, 10);
        Map<Banknote, Integer> result = algorithm.calculate(16500, cassettes);

        assertEquals(3, result.get(new Banknote(5000)));   // 15000
        assertEquals(1, result.get(new Banknote(1000)));    // 1000
        assertEquals(1, result.get(new Banknote(500)));     // 500
    }
}

package atm.model;

import atm.exception.CassetteOverflowException;
import atm.exception.InsufficientFundsInCassetteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CassetteTest {

    private Cassette cassette;

    @BeforeEach
    void setUp() {
        cassette = new Cassette(new Banknote(1000), 100, 10);
    }

    @Test
    void constructor_shouldSetInitialState() {
        assertEquals(10, cassette.getAvailableCount());
        assertEquals(1000, cassette.getDenomination().getDenomination());
        assertEquals(10000, cassette.getAvailableAmount());
    }

    @Test
    void constructor_shouldThrowOnNegativeInitialCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cassette(new Banknote(100), 100, -1));
    }

    @Test
    void constructor_shouldThrowWhenInitialCountExceedsCapacity() {
        assertThrows(CassetteOverflowException.class,
                () -> new Cassette(new Banknote(100), 5, 10));
    }

    @Test
    void put_shouldAddCount() {
        cassette.put(5);
        assertEquals(15, cassette.getAvailableCount());
    }

    @Test
    void put_shouldThrowOnNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> cassette.put(-1));
    }

    @Test
    void put_shouldThrowOnOverflow() {
        assertThrows(CassetteOverflowException.class, () -> cassette.put(91));
    }

    @Test
    void put_shouldNotThrowWhenExactlyAtCapacity() {
        assertDoesNotThrow(() -> cassette.put(90)); // 10 + 90 = 100
        assertEquals(100, cassette.getAvailableCount());
    }

    @Test
    void take_shouldReduceCount() {
        cassette.take(3);
        assertEquals(7, cassette.getAvailableCount());
    }

    @Test
    void take_shouldThrowWhenInsufficientFunds() {
        assertThrows(InsufficientFundsInCassetteException.class, () -> cassette.take(15));
    }

    @Test
    void take_zeroShouldReduceCount() {
        cassette.take(0);
        assertEquals(10, cassette.getAvailableCount());
    }

    @Test
    void take_shouldFullyEmptyCassette() {
        cassette.take(10);
        assertEquals(0, cassette.getAvailableCount());
    }

    @Test
    void getAvailableAmount_shouldReturnDenominationTimesCount() {
        cassette.put(5); // 10 + 5 = 15
        assertEquals(15000, cassette.getAvailableAmount()); // 15 * 1000
    }
}

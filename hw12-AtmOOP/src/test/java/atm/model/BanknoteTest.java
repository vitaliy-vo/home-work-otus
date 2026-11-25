package atm.model;

import atm.exception.CassetteOverflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BanknoteTest {

    @Test
    void constructor_shouldSetDenomination() {
        Banknote note = new Banknote(1000);
        assertEquals(1000, note.getDenomination());
    }

    @Test
    void constructor_shouldThrowOnNegativeDenomination() {
        assertThrows(IllegalArgumentException.class, () -> new Banknote(-100));
    }

    @Test
    void constructor_shouldThrowOnZeroDenomination() {
        assertThrows(IllegalArgumentException.class, () -> new Banknote(0));
    }

    @Test
    void equals_shouldReturnTrueForSameDenomination() {
        Banknote a = new Banknote(5000);
        Banknote b = new Banknote(5000);
        assertEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalseForDifferentDenomination() {
        Banknote a = new Banknote(1000);
        Banknote b = new Banknote(5000);
        assertNotEquals(a, b);
    }

    @Test
    void hashCode_shouldBeSameForSameDenomination() {
        Banknote a = new Banknote(1000);
        Banknote b = new Banknote(1000);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toString_shouldContainDenomination() {
        Banknote note = new Banknote(2000);
        assertTrue(note.toString().contains("2000"));
    }
}

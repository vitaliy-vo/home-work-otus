package atm;

import atm.exception.CannotDispenseAmountException;
import atm.exception.CassetteOverflowException;
import atm.exception.InsufficientFundsInCassetteException;
import atm.exception.InvalidDenominationException;
import atm.model.Banknote;
import atm.strategy.GreedyDispensingAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtmTest {

    private Atm atm;

    @BeforeEach
    void setUp() {
        atm = new Atm(new GreedyDispensingAlgorithm());
        atm.loadCassette(new Banknote(5000), 100, 10);
        atm.loadCassette(new Banknote(1000), 200, 20);
        atm.loadCassette(new Banknote(100), 500, 50);
    }

    // ========== loadCassette ==========

    @Test
    void loadCassette_shouldAddCassette() {
        assertTrue(atm.getTotalBalance() > 0);
    }

    @Test
    void loadCassette_shouldThrowWhenCassetteExists() {
        assertThrows(IllegalStateException.class,
                () -> atm.loadCassette(new Banknote(5000), 100, 5));
    }

    // ========== acceptBanknotes ==========

    @Test
    void acceptBanknotes_shouldAddMoney() {
        int balanceBefore = atm.getTotalBalance();
        atm.acceptBanknotes(new Banknote(1000), 5);
        assertEquals(balanceBefore + 5000, atm.getTotalBalance());
    }

    @Test
    void acceptBanknotes_shouldThrowWhenNoCassette() {
        assertThrows(IllegalStateException.class,
                () -> atm.acceptBanknotes(new Banknote(50), 10));
    }

    @Test
    void acceptBanknotes_shouldThrowOnOverflow() {
        // Кассета 100-рублёвок имеет capacity=500, сейчас 50
        assertThrows(CassetteOverflowException.class,
                () -> atm.acceptBanknotes(new Banknote(100), 460));
    }

    // ========== withdraw ==========

    @Test
    void withdraw_shouldDispenseExactAmount() {
        int balanceBefore = atm.getTotalBalance();
        atm.withdraw(17000); // 3x5000 + 2x1000
        assertEquals(balanceBefore - 17000, atm.getTotalBalance());
    }

    @Test
    void withdraw_shouldThrowOnNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(-100));
    }

    @Test
    void withdraw_shouldThrowOnZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(0));
    }

    @Test
    void withdraw_shouldThrowWhenCannotDispense() {
        assertThrows(CannotDispenseAmountException.class, () -> atm.withdraw(50));
    }

    @Test
    void withdraw_shouldThrowWhenInsufficientFundsInCassettes() {
        // Запрос больше, чем есть в кассетах
        assertThrows(CannotDispenseAmountException.class, () -> atm.withdraw(999999));
    }

    @Test
    void withdraw_shouldReduceCassetteCounts() {
        atm.withdraw(5000);
        // Кассета 5000: было 10, стало 7
        // Проверяем через баланс
        assertEquals(70000, atm.getTotalBalance()); // 7*5000 + 20*1000 + 50*100 = 35000+20000+5000 = 60000
    }

    // ========== getTotalBalance ==========

    @Test
    void getTotalBalance_shouldReturnCorrectTotal() {
        assertEquals(75000, atm.getTotalBalance()); // 10*5000 + 20*1000 + 50*100
    }

    @Test
    void getTotalBalance_shouldReflectDeposits() {
        atm.acceptBanknotes(new Banknote(5000), 2);
        assertEquals(85000, atm.getTotalBalance());
    }

    @Test
    void getTotalBalance_shouldReflectWithdrawals() {
        atm.withdraw(10000);
        assertEquals(65000, atm.getTotalBalance());
    }

    // ========== Интеграционные ==========

    @Test
    void fullCycle_depositAndWithdraw() {
        int balanceBefore = atm.getTotalBalance();

        atm.acceptBanknotes(new Banknote(5000), 3);
        atm.withdraw(15000);

        assertEquals(balanceBefore, atm.getTotalBalance());
    }

    @Test
    void fullCycle_multipleWithdrawals() {
        atm.withdraw(5000);
        atm.withdraw(1000);
        atm.withdraw(100);

        assertEquals(68900, atm.getTotalBalance()); // 75000 - 5000 - 1000 - 100
    }

    @Test
    void withdraw_shouldNotLeavePartialRemainder() {
        // 17300 = 3x5000 + 2x1000 + 3x100
        atm.withdraw(17300);
        assertEquals(57700, atm.getTotalBalance()); // 75000 - 17300
    }
}

package atm.exception;


public class CassetteOverflowException extends AtmException {
    private final int capacity;
    private final int attemptedToAdd;

    public CassetteOverflowException(int capacity, int attemptedToAdd) {
        super(String.format("Ячейка переполнена. Вместимость: %d, пытаются добавить: %d",
                capacity, attemptedToAdd));
        this.capacity = capacity;
        this.attemptedToAdd = attemptedToAdd;
    }

    public int getCapacity() { return capacity; }
    public int getAttemptedToAdd() { return attemptedToAdd; }
}

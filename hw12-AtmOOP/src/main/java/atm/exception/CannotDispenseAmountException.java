package atm.exception;

public class CannotDispenseAmountException extends AtmException {
    private final int requestedAmount;
    private final int remainder;

    public CannotDispenseAmountException(int requestedAmount, int remainder) {
        super(String.format("Невозможно выдать сумму %d. Не разменивается остаток: %d",
                requestedAmount, remainder));
        this.requestedAmount = requestedAmount;
        this.remainder = remainder;
    }

    public int getRequestedAmount() { return requestedAmount; }
    public int getRemainder() { return remainder; }
}
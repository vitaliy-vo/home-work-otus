package atm.exception;

public class InvalidDenominationException extends AtmException {
    private final int denomination;

    public InvalidDenominationException(int denomination) {
        super("В банкомате нет ячейки для номинала " + denomination);
        this.denomination = denomination;
    }

    public int getDenomination() { return denomination; }
}

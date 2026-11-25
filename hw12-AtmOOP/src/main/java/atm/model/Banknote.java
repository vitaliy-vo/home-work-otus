package atm.model;

import java.util.Objects;

public final class Banknote {
    private final int denomination;

    public Banknote(int denomination) {
        if (denomination <= 0) {
            throw new IllegalArgumentException("Номинал должен быть положительным");
        }
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return denomination == banknote.denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }

    @Override
    public String toString() {
        return denomination + " руб.";
    }
}

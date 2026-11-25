package atm.strategy;

import atm.model.Banknote;
import atm.model.Cassette;
import java.util.Map;

public interface DispensingAlgorithm {
    Map<Banknote, Integer> calculate(int amount, Map<Banknote, Cassette> cassettes);
}
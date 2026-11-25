package atm.exception;

public class InsufficientFundsInCassetteException extends AtmException{
    public InsufficientFundsInCassetteException(String message){
        super(message);
    }
}

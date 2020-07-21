package exception.data;

public class RideFullException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "The requested ride is already full!";

    public RideFullException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    @Override
    public String toString() {
        return EXCEPTION_MESSAGE;
    }
}
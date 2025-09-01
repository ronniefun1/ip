package reboot;

public class RebootException extends RuntimeException {
    public RebootException(String message) {
        super("Reboot error: " + message);
    }
}
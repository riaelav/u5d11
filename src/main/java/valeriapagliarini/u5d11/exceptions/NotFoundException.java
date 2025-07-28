package valeriapagliarini.u5d11.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Element with ID " + id + " not found.");
    }
}

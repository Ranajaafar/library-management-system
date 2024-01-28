package maids.cc.librarymanagementsystem.patron.exception;

public class PatronNotFoundException extends RuntimeException {

    public PatronNotFoundException(long patronId) {
        super("Patron with id "+ patronId +" not found.");
    }
}
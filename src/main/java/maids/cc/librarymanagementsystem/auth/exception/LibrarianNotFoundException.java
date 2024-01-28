package maids.cc.librarymanagementsystem.auth.exception;

public class LibrarianNotFoundException extends RuntimeException{

    public LibrarianNotFoundException(String username) {
        super("Librarian with username "+ username +" not found.");
    }

}

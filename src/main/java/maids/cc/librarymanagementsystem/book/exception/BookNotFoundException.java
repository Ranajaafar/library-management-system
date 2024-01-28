package maids.cc.librarymanagementsystem.book.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(long bookId) {
        super("Book with id "+ bookId +" not found.");
    }
}

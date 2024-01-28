package maids.cc.librarymanagementsystem.borrowingrecord.exception;

public class BookIsAlreadyBorrowedException extends RuntimeException {

    public BookIsAlreadyBorrowedException(long bookId) {
        super("Book with id "+ bookId +" is already borrowed.");
    }
}
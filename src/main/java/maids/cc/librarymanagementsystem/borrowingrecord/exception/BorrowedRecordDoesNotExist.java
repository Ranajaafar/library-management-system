package maids.cc.librarymanagementsystem.borrowingrecord.exception;

public class BorrowedRecordDoesNotExist extends RuntimeException{

        public BorrowedRecordDoesNotExist(long bookId, long patronId) {
            super("patron id "+patronId+" didn't borrow a book id "+ bookId +".");
        }
}

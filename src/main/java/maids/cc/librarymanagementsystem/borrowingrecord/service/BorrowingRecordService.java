package maids.cc.librarymanagementsystem.borrowingrecord.service;

public interface BorrowingRecordService {
    String borrow(Long bookId, Long patronId);

    String returnBook(Long bookId, Long patronId);
}

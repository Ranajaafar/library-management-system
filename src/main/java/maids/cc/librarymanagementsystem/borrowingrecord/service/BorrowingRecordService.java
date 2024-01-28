package maids.cc.librarymanagementsystem.borrowingrecord.service;

import maids.cc.librarymanagementsystem.borrowingrecord.entity.BorrowingRecord;

import java.util.List;

public interface BorrowingRecordService {
    String borrow(Long bookId, Long patronId);

    String returnBook(Long bookId, Long patronId);
}

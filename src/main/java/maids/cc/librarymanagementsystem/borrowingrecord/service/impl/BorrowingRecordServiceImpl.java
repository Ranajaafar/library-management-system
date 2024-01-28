package maids.cc.librarymanagementsystem.borrowingrecord.service.impl;

import lombok.RequiredArgsConstructor;
import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.book.exception.BookNotFoundException;
import maids.cc.librarymanagementsystem.book.repository.BookRepository;
import maids.cc.librarymanagementsystem.borrowingrecord.entity.BorrowingRecord;
import maids.cc.librarymanagementsystem.borrowingrecord.exception.BookIsAlreadyBorrowedException;
import maids.cc.librarymanagementsystem.borrowingrecord.exception.BorrowedRecordDoesNotExist;
import maids.cc.librarymanagementsystem.borrowingrecord.repository.BorrowingRecordRepository;
import maids.cc.librarymanagementsystem.borrowingrecord.service.BorrowingRecordService;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.exception.PatronNotFoundException;
import maids.cc.librarymanagementsystem.patron.repository.PatronRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepositroy;
    private final PatronRepository patronRepository;


    @Override
    @Transactional
    public String borrow(Long bookId, Long patronId) {
        if(borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(bookId))
            throw new BookIsAlreadyBorrowedException(bookId);

        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));
        Book book = bookRepositroy.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        BorrowingRecord borrowingRecord= new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecordRepository.save(borrowingRecord);

        return "book is borrowed successfully";
    }



    @Override
    public String returnBook(Long bookId, Long patronId) {
        if(!borrowingRecordRepository.existsByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId))
            throw new BorrowedRecordDoesNotExist(bookId, patronId);

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        borrowingRecord.setReturnDate(new Date());
        borrowingRecordRepository.save(borrowingRecord);

        return "book is returned successfully";
    }
}

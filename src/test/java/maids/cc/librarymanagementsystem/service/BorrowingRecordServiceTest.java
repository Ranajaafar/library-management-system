package maids.cc.librarymanagementsystem.service;


import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.book.enums.Language;
import maids.cc.librarymanagementsystem.book.exception.BookNotFoundException;
import maids.cc.librarymanagementsystem.book.repository.BookRepository;
import maids.cc.librarymanagementsystem.borrowingrecord.entity.BorrowingRecord;
import maids.cc.librarymanagementsystem.borrowingrecord.exception.BookIsAlreadyBorrowedException;
import maids.cc.librarymanagementsystem.borrowingrecord.exception.BorrowedRecordDoesNotExist;
import maids.cc.librarymanagementsystem.borrowingrecord.repository.BorrowingRecordRepository;
import maids.cc.librarymanagementsystem.borrowingrecord.service.impl.BorrowingRecordServiceImpl;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.exception.PatronNotFoundException;
import maids.cc.librarymanagementsystem.patron.repository.PatronRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {
    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepositroy;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private Book book;
    private Patron patron;

    @BeforeEach
    public void setup() throws Exception {
        Date date=new Date();
        book=new Book();
        book.setId(1L);
        book.setIsbn("12345678912");
        book.setTitle("title");
        book.setAuthor("author");
        book.setPublicationDate(date);
        book.setLanguage(Language.ARABIC);
        book.setDescription("description");

        patron=new Patron();
        patron.setId(1L);
        patron.setName("name");
        patron.setEmail("username@gmail.com");
        patron.setPhoneNumber("70777111");
    }

    @Test
    public void borrowTest(){
        //arrange
        when(borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(any(Long.class))).thenReturn(false);
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.of(patron));
        when(bookRepositroy.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(any(BorrowingRecord.class));

        //act
        String result=borrowingRecordService.borrow(1L,1L);

        //assert
        Assertions.assertThat(result).isNotNull();
        assertEquals(result,"book is borrowed successfully");
    }

    @Test
    public void borrowTest_BookIsAlreadyBorrowed(){
        //arrange
        when(borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(any(Long.class))).thenReturn(true);

        //act
        BookIsAlreadyBorrowedException exception = assertThrows(BookIsAlreadyBorrowedException.class, () -> {
            borrowingRecordService.borrow(1L,1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Book with id 1 is already borrowed.");
    }

    @Test
    public void borrowTest_PatronNotFound(){
        //arrange
        when(borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(any(Long.class))).thenReturn(false);
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        //act
        PatronNotFoundException exception = assertThrows(PatronNotFoundException.class, () -> {
            borrowingRecordService.borrow(1L,1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Patron with id 1 not found.");
    }

    @Test
    public void borrowTest_BookNotFound(){
        //arrange
        when(borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(any(Long.class))).thenReturn(false);
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.of(patron));
        when(bookRepositroy.findById(any(Long.class))).thenReturn(Optional.empty());


        //act
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            borrowingRecordService.borrow(1L,1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Book with id 1 not found.");
    }

    @Test
    public void returnBookTest(){
        //arrange
        when(borrowingRecordRepository.existsByBookIdAndPatronIdAndReturnDateIsNull(any(Long.class),any(Long.class))).thenReturn(true);
        BorrowingRecord borrowingRecord=new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBorrowDate(new Date());
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(any(Long.class),any(Long.class))).thenReturn(borrowingRecord);

        //act
        String result=borrowingRecordService.returnBook(1L,1L);

        //assert
        assertEquals(result,"book is returned successfully");
    }


    @Test
    public void returnBookTest_BorrowedRecordDoesNotExist(){
        //arrange
        when(borrowingRecordRepository.existsByBookIdAndPatronIdAndReturnDateIsNull(any(Long.class),any(Long.class))).thenReturn(false);

        //act
        BorrowedRecordDoesNotExist exception = assertThrows(BorrowedRecordDoesNotExist.class, () -> {
            borrowingRecordService.returnBook(1L,1L);
        });

        //assert
        assertEquals(exception.getMessage(),"patron id 1 didn't borrow a book id 1.");

    }

}

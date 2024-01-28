package maids.cc.librarymanagementsystem.service;


import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.book.enums.Language;
import maids.cc.librarymanagementsystem.book.exception.BookNotFoundException;
import maids.cc.librarymanagementsystem.book.mapper.BookMapper;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.book.repository.BookRepository;
import maids.cc.librarymanagementsystem.book.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Spy
    private BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

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

    }
    @Test
    public void getAll(){
        //arrange
        when(bookRepository.findAll()).thenReturn(List.of(book));

        //act
        List<BookDto> result=bookService.getAll();

        //assert
        Assertions.assertThat(result).isNotNull();
        assertEquals(result.size(),1);
        assertEquals(result.get(0).id(),book.getId());
    }

    @Test
    public void getAll_nullList(){
        //arrange
        when(bookRepository.findAll()).thenReturn(null);

        //act
        List<BookDto> result=bookService.getAll();

        //assert
        Assertions.assertThat(result).isNull();
    }
    @Test
    public void findById(){
        //arrange
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        //act
        BookDto result=bookService.findById(1L);

        //assert
        Assertions.assertThat(result).isNotNull();
        assertEquals(result.id(),book.getId());

    }

    @Test
    public void findByIdNotFound(){
        //arrange
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //act
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findById(1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Book with id 1 not found.");
    }

    @Test
    public void createTest() throws Exception {
        //arrange
        when(bookMapper.toBook(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        //act
        BookRequest bookRequest=new BookRequest("12345678912","title","author",new Date(), Language.ARABIC,"description");
        BookDto result=bookService.create(bookRequest);

        //assert
        Assertions.assertThat(result).isNotNull();
        assertEquals(result.id(),book.getId());
        assertEquals(result.language(),book.getLanguage());
        assertEquals(result.description(),book.getDescription());
    }

    @Test
    public void deleteTest(){
        //arrange
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(any(Long.class));

        //act
        bookService.delete(1L);
    }

    @Test
    public void deleteTest_NotFound(){
        //arrange
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //act
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.delete(1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Book with id 1 not found.");
    }

    @Test
    public void updateTest(){
        //arrange
        Date date=new Date();
        Book newBook=new Book();
        newBook.setId(1L);
        newBook.setIsbn("12345678913");
        newBook.setTitle("title1");
        newBook.setAuthor("author1");
        newBook.setPublicationDate(date);
        newBook.setLanguage(Language.ENGLISH);
        newBook.setDescription("description1");
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);


        //act
        BookRequest bookRequest=new BookRequest("12345678913","title1","author1",date, Language.ENGLISH,"description1");
        BookDto result=bookService.update(bookRequest,1L);

        //assert
        Assertions.assertThat(result).isNotNull();
        assertEquals(result.id(),book.getId());
        assertEquals(result.isbn(),newBook.getIsbn());
        assertEquals(result.title(),newBook.getTitle());
        assertEquals(result.author(),newBook.getAuthor());
        assertEquals(result.publicationDate(),newBook.getPublicationDate());
    }

    @Test
    public void updateTest_NotFound(){
        //arrange
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //act
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.update(null,1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Book with id 1 not found.");
    }

}

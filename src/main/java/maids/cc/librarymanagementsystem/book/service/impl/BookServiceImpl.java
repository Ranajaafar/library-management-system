package maids.cc.librarymanagementsystem.book.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.book.exception.BookNotFoundException;
import maids.cc.librarymanagementsystem.book.mapper.BookMapper;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.book.repository.BookRepository;
import maids.cc.librarymanagementsystem.book.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.*;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    //cache methods with heavy computation or db calls like getting a full list of books
    @Cacheable(value = "books") //cache a list of BookDto
    public List<BookDto> getAll() {
        log.info("Fetch all books from db");

        return bookMapper.toDtoList(bookRepository.findAll());
    }

    @Override
    @Cacheable(key = "#id",value = "book")
    public BookDto findById(Long id) {
        log.info("fetch book by id: {} from db", id);

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toBookDto(book);
    }

    @Override
    // every time we create,delete and update to db we should clear the cache that has the list of all books
    @CacheEvict(value = "books", allEntries = true)
    public BookDto create(BookRequest bookRequest) {
        log.info("save book to db");

        //get the saved book from db and use it to return the dto
        Book savedBook = bookRepository.save(bookMapper.toBook(bookRequest));
        return bookMapper.toBookDto(savedBook);
    }

    @Override
    //we should clear the cache that has the list of all books and clear the cache that has the book with the given id
    @Caching(evict = {
            @CacheEvict(value = "book", key = "#id"),
            @CacheEvict(value = "books", allEntries = true)
    })
    public void delete(long id) {
        log.info("delete book from db");

        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.deleteById(id);
    }

    @Override
    @Caching(put={
            @CachePut(value="book", key="#id")},
            evict={
                    @CacheEvict(value="books", allEntries=true)}
    )
    public BookDto update(BookRequest bookRequest, Long id) {
        log.info("update book from db");

        //we need to check whether employees with given id exist in DB or not
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setIsbn(bookRequest.getIsbn());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setIsbn(bookRequest.getIsbn());
        book.setLanguage(bookRequest.getLanguage());
        book.setPublicationDate(bookRequest.getPublicationDate());
        book.setTitle(bookRequest.getTitle());
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toBookDto(updatedBook);
    }

}

package maids.cc.librarymanagementsystem.book.service;

import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();
    BookDto findById(Long id);

    BookDto create(BookRequest bookRequest);
    void delete(long id);
    BookDto update(BookRequest bookRequest, Long id);
}

package maids.cc.librarymanagementsystem.book.mapper;


import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toBookDto(Book book);
    List<BookDto> toDtoList(List<Book> bookList);
    Book toBook(BookRequest bookRequest);
}

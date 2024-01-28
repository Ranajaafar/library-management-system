package maids.cc.librarymanagementsystem.book.mapper;


import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toBookDto(Book book);
    List<BookDto> toDtoList(List<Book> bookList);
    Book toBook(BookRequest bookRequest);
}

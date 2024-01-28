package maids.cc.librarymanagementsystem.book.payload.response;

import maids.cc.librarymanagementsystem.book.enums.Language;

import java.io.Serializable;
import java.util.Date;



public record BookDto(Long id,
                      String isbn,
                      String title,
                      String author,
                      Date publicationDate,
                      Language language,
                      String description) implements Serializable{
}
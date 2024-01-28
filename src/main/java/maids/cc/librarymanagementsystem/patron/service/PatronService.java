package maids.cc.librarymanagementsystem.patron.service;

import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;

import java.util.List;

public interface PatronService {

    List<PatronDto> getAll();
    PatronDto findById(Long id);

    PatronDto create(PatronRequest patronRequest);
    void delete(long id);
    PatronDto update(PatronRequest patronRequest, Long id);
}

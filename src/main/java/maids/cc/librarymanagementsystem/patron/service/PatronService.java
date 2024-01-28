package maids.cc.librarymanagementsystem.patron.service;

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

package maids.cc.librarymanagementsystem.patron.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.exception.PatronNotFoundException;
import maids.cc.librarymanagementsystem.patron.mapper.PatronMapper;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;
import maids.cc.librarymanagementsystem.patron.repository.PatronRepository;
import maids.cc.librarymanagementsystem.patron.service.PatronService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    private final PatronMapper patronMapper;

    @Override
    //cache methods with heavy computation or db calls like getting a full list of Patrons
    @Cacheable(value = "patrons") //cache a list of PatronDto
    public List<PatronDto> getAll() {
        log.info("Fetch all patrons from db");

        return patronMapper.toDtoList(patronRepository.findAll());
    }

    @Override
    @Cacheable(key = "#id",value = "patron")
    public PatronDto findById(Long id) {
        log.info("fetch patron by id: {} from db", id);

        Patron patron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));
        return patronMapper.toDto(patron);
    }

    @Override
    // every time we create,delete and update to db we should clear the cache that has the list of all patrons
    @CacheEvict(value = "patrons", allEntries = true)
    public PatronDto create(PatronRequest patronRequest) {
        log.info("save patron to db");

        //get the saved patron from db and use it to return the dto
        Patron savedPatron = patronRepository.save(patronMapper.toPatron(patronRequest));
        return patronMapper.toDto(savedPatron);
    }

    @Override
    //we should clear the cache that has the list of all patrons and clear the cache that has the patron with the given id
    @Caching(evict = {
            @CacheEvict(value = "patron", key = "#id"),
            @CacheEvict(value = "patrons", allEntries = true)
    })
    public void delete(long id) {
        log.info("delete patron from db");

        patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));
        patronRepository.deleteById(id);
    }

    @Override
    @Caching(put={
            @CachePut(value="patron", key="#id")},
            evict={
                    @CacheEvict(value="patrons", allEntries=true)}
    )
    public PatronDto update(PatronRequest patronRequest, Long id) {
        log.info("update patron from db");

        //we need to check whether patrons with given id exist in DB or not
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));

        patron.setName(patronRequest.getName());
        patron.setEmail(patronRequest.getEmail());
        patron.setPhoneNumber(patronRequest.getPhoneNumber());
        Patron updatedPatron = patronRepository.save(patron);
        return patronMapper.toDto(updatedPatron);
    }
}

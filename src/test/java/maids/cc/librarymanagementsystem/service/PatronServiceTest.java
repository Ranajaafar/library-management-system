package maids.cc.librarymanagementsystem.service;


import maids.cc.librarymanagementsystem.book.mapper.BookMapper;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.exception.PatronNotFoundException;
import maids.cc.librarymanagementsystem.patron.mapper.PatronMapper;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;
import maids.cc.librarymanagementsystem.patron.repository.PatronRepository;
import maids.cc.librarymanagementsystem.patron.service.impl.PatronServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {
    @Mock
    private PatronRepository patronRepository;

    @Spy
    private PatronMapper patronMapper = Mappers.getMapper(PatronMapper.class);

    @InjectMocks
    private PatronServiceImpl patronService;

    private Patron patron;
    @BeforeEach
    public void setup() throws Exception {
        patron=new Patron();
        patron.setId(1L);
        patron.setName("name");
        patron.setEmail("username@gmail.com");
        patron.setPhoneNumber("70777111");
    }

    @Test
    public void getAllTest(){
        //arrange
        when(patronRepository.findAll()).thenReturn(List.of(patron));


        //act
        List<PatronDto> result=patronService.getAll();

        //assert
        assertEquals(result.size(),1);
        assertEquals(result.get(0).id(),patron.getId());
        assertEquals(result.get(0).name(),patron.getName());
    }

    @Test
    public void getAllTest_nullList(){
        //arrange
        when(patronRepository.findAll()).thenReturn(null);

        //act
        List<PatronDto> result=patronService.getAll();

        //assert
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void findByIdTest() {
        //arrange
        when(patronRepository.findById(1L)).thenReturn(java.util.Optional.of(patron));


        //act
        PatronDto result = patronService.findById(1L);

        //assert
        assertEquals(result.id(), patron.getId());
        assertEquals(result.name(), patron.getName());
    }

    @Test
    public void findByIdTest_NotFound(){
        //arrange
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //act
        PatronNotFoundException exception = assertThrows(PatronNotFoundException.class, () -> {
            patronService.findById(1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Patron with id 1 not found.");
    }

    @Test
    public void createTest() {
        //arrange
        when(patronMapper.toPatron(any(PatronRequest.class))).thenReturn(patron);
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);


        //act
        PatronRequest patronRequest = new PatronRequest("name", "username@gmail.com", "70777111");
        PatronDto result = patronService.create(patronRequest);

        //assert
        assertEquals(result.id(), patron.getId());
        assertEquals(result.name(), patron.getName());
    }

    @Test
    public void deleteTest() {
        //arrange
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.of(patron));

        //act
        patronService.delete(1L);
    }

    @Test
    public void deleteTest_NotFound(){
        //arrange
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //act
        PatronNotFoundException exception = assertThrows(PatronNotFoundException.class, () -> {
            patronService.delete(1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Patron with id 1 not found.");
    }

    @Test
    public void updateTest() {
        //arrange
        Patron newPatron=new Patron();
        patron.setId(1L);
        patron.setName("name1");
        patron.setEmail("username1@gmail.com");
        patron.setPhoneNumber("70777222");
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(newPatron);


        //act
        PatronRequest patronRequest = new PatronRequest("name1", "username1@gmail.com","70777222");
        PatronDto result = patronService.update(patronRequest,1L);

        //assert
        Assertions.assertThat(result).isNotNull();
        assertEquals(result.id(), newPatron.getId());
        assertEquals(result.name(), newPatron.getName());
        assertEquals(result.email(), newPatron.getEmail());
        assertEquals(result.phoneNumber(), newPatron.getPhoneNumber());
    }

    @Test
    public void updateTest_NotFound(){
        //arrange
        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //act
        PatronNotFoundException exception = assertThrows(PatronNotFoundException.class, () -> {
            patronService.update(any(PatronRequest.class),1L);
        });

        //assert
        assertEquals(exception.getMessage(),"Patron with id 1 not found.");
    }
}

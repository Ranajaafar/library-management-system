package maids.cc.librarymanagementsystem.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import maids.cc.librarymanagementsystem.book.enums.Language;
import maids.cc.librarymanagementsystem.book.exception.BookNotFoundException;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.book.service.BookService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;



    @Test
    public void getAllTest() throws Exception {
        when(bookService.getAll()).thenReturn(Arrays.asList());

        ResultActions response = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(0)));

    }

    @Test
    public void getByIdTest() throws Exception {
        BookDto bookDto=new BookDto(1L,"12345678912","title","author",new Date(), Language.ARABIC,"description");
        when(bookService.findById(1L)).thenReturn(bookDto);

        ResultActions response = mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is("author")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", CoreMatchers.is("12345678912")));

    }

    @Test
    public void getByIdTest_NotFound() throws Exception {
        when(bookService.findById(1L)).thenThrow(new BookNotFoundException(1L));;

        ResultActions response = mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book with id 1 not found."));
    }


    @Test
    public void issueTest() throws Exception {
        BookDto bookDto=new BookDto(1L,"12345678912","title","author",new Date(), Language.ARABIC,"description");
        when(bookService.create(any(BookRequest.class))).thenReturn(bookDto);

        BookRequest bookRequest=new BookRequest("12345678912","title","author",new Date(), Language.ARABIC,"description");
        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is("author")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", CoreMatchers.is("12345678912")));

    }

    @Test
    public void issueTest_duplicateIsbn() throws Exception {
        when(bookService.create(any(BookRequest.class))).thenThrow(new RuntimeException("could not execute statement [ERROR: duplicate key value violates unique constraint \"uk_ehpdfjpu1jm3hijhj4mm0hx9h\"\n" +
                "  Detail: Key (isbn)=(5904351843) already exists.] [insert into book (author,description,isbn,language,publication_date,title) values (?,?,?,?,?,?)]; SQL [insert into book (author,description,isbn,language,publication_date,title) values (?,?,?,?,?,?)]; constraint [uk_ehpdfjpu1jm3hijhj4mm0hx9h]"));

        BookRequest bookRequest=new BookRequest("5904351843","title","author",new Date(), Language.ARABIC,"description");
        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)));

        response.andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("could not execute statement [ERROR: duplicate key value violates unique constraint \"uk_ehpdfjpu1jm3hijhj4mm0hx9h\"\n" +
                        "  Detail: Key (isbn)=(5904351843) already exists.] [insert into book (author,description,isbn,language,publication_date,title) values (?,?,?,?,?,?)]; SQL [insert into book (author,description,isbn,language,publication_date,title) values (?,?,?,?,?,?)]; constraint [uk_ehpdfjpu1jm3hijhj4mm0hx9h]"));

    }

    @Test
    public void issueTest_MethodArgumentIsbnNotBlank() throws Exception {

        BookRequest bookRequest=new BookRequest(null,"title","author",new Date(), Language.ARABIC,"description");
        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("[\"isbn should not be null and must have at least one non-whitespace character\"]"));
    }

    @Test
    public void deleteTest() throws Exception {
        doNothing().when(bookService).delete(any(Long.class));

        mockMvc.perform(delete("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("book deleted"));

    }

    @Test
    public void updateTest() throws Exception {
        BookDto bookDto=new BookDto(1L,"12345678912","title","author",new Date(), Language.ENGLISH,"description");
        when(bookService.update(any(BookRequest.class),any(Long.class))).thenReturn(bookDto);

        BookRequest bookRequest=new BookRequest("12345678912","title","author",new Date(), Language.ENGLISH,"description");
        ResultActions response = mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is("author")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", CoreMatchers.is("12345678912")));

    }

}

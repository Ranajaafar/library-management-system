package maids.cc.librarymanagementsystem.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import maids.cc.librarymanagementsystem.book.enums.Language;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.service.BookService;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;
import maids.cc.librarymanagementsystem.patron.service.PatronService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatronService patronService;

    @Test
    public void getAllTest() throws Exception {
        when(patronService.getAll()).thenReturn(Arrays.asList());

        ResultActions response = mockMvc.perform(get("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(0)));

    }

    @Test
    public void getByIdTest() throws Exception {
        PatronDto patronDto=new PatronDto(1L,"name","username@gmail.com","70777111");
        when(patronService.findById(1L)).thenReturn(patronDto);

        ResultActions response = mockMvc.perform(get("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("name")));

    }

    @Test
    public void issueTest() throws Exception {
        PatronDto patronDto=new PatronDto(1L,"name","username@gmail.com","70777111");
        when(patronService.create(any(PatronRequest.class))).thenReturn(patronDto);

        PatronRequest patronRequest=new PatronRequest("name","username@gmail.com","70777111");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)));

    }

    @Test
    public void updateTest() throws Exception {
        PatronDto patronDto = new PatronDto(1L, "name", "username@gmail.com", "70777111");
        when(patronService.update(any(PatronRequest.class), any(Long.class))).thenReturn(patronDto);

        PatronRequest patronRequest=new PatronRequest("name","username@gmail.com","70777111");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("name")));
    }

    @Test
    public void deleteTest() throws Exception {
        doNothing().when(patronService).delete(any(Long.class));

        mockMvc.perform(delete("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("patron deleted"));

    }

}

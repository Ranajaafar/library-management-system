package maids.cc.librarymanagementsystem.api;


import maids.cc.librarymanagementsystem.borrowingrecord.exception.BookIsAlreadyBorrowedException;
import maids.cc.librarymanagementsystem.borrowingrecord.service.impl.BorrowingRecordServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordServiceImpl borrowingRecordService;


    @Test
    public void borrowTest() throws Exception {
        when(borrowingRecordService.borrow(any(Long.class),any(Long.class))).thenReturn("book is borrowed successfully");

        ResultActions response = mockMvc.perform(post("/api/borrow/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("book is borrowed successfully"));
    }

    @Test
    public void borrowTest_BookIsAlreadyBorrowed() throws Exception {
        when(borrowingRecordService.borrow(any(Long.class),any(Long.class))).thenThrow(new BookIsAlreadyBorrowedException(1L));

        ResultActions response = mockMvc.perform(post("/api/borrow/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("Book with id 1 is already borrowed."));
    }

    @Test
    public void returnBook() throws Exception {
        when(borrowingRecordService.returnBook(any(Long.class),any(Long.class))).thenReturn("book is returned successfully");

        ResultActions response = mockMvc.perform(put("/api/return/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("book is returned successfully"));
    }

}

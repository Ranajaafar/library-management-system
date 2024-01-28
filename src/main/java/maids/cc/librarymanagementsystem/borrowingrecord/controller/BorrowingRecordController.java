package maids.cc.librarymanagementsystem.borrowingrecord.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.borrowingrecord.service.BorrowingRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Configuration: Borrowing APIs", description = "Borrowing endpoints")
@RequestMapping("/api")
@SecurityRequirement(name = "Bearer Authentication")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    @Operation(summary = "borrowing api", description = " Allow a patron to borrow a book."  )
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrow(@PathVariable("bookId") Long bookId, @PathVariable("patronId") Long patronId){
        log.info("Request to borrow book: {} from patron: {}", bookId,patronId);

        return ResponseEntity.ok(borrowingRecordService.borrow(bookId,patronId));
    }

    @Operation(summary = "return api", description = " Allow a patron to return a book."  )
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable("bookId") Long bookId, @PathVariable("patronId") Long patronId){
        log.info("Request to return book: {} from patron: {}", bookId,patronId);

        return ResponseEntity.ok(borrowingRecordService.returnBook(bookId,patronId));
    }
}

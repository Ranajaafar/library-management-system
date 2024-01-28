package maids.cc.librarymanagementsystem.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.book.payload.request.BookRequest;
import maids.cc.librarymanagementsystem.book.payload.response.BookDto;
import maids.cc.librarymanagementsystem.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Configuration: Book APIs", description = "Book management endpoints")
@RequestMapping("/api/books")
@SecurityRequirement(name = "Bearer Authentication")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get all books", description = " Retrieve a list of all books."  )
    @GetMapping()
    public ResponseEntity<List<BookDto>> getAll(){
        log.info("Request to get all Books");

        return ResponseEntity.ok(bookService.getAll());
    }


    @Operation(
            summary = "Get a Book by ID",
            description = "Retrieve details of a specific book by ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getProduct(@PathVariable("id") Long id){
        log.info("Request to get Book by id: {}", id);

        return ResponseEntity.ok(bookService.findById(id));
    }


    //build create product REST API
    @Operation(
            summary = "Add a book",
            description = "Add a new book to the library."
    )
    @PostMapping()
    public ResponseEntity<BookDto> issue(@Valid @RequestBody BookRequest request) throws Exception {
        log.info("Request to create new Book: {}", request);

        return new ResponseEntity<>(bookService.create(request), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Remove a book",
            description = "Remove a book from the library."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        log.info("Request to delete Book: {}", id);

        bookService.delete(id);
        return ResponseEntity.ok("book deleted");

    }


    @Operation(
            summary = "Update a book",
            description = "Update an existing book's information."
    )
    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody BookRequest request){
        log.info("Request to update Book: {}", id);

        return ResponseEntity.ok(bookService.update(request,id));
    }
}

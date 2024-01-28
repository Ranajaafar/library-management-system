package maids.cc.librarymanagementsystem.common;

import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.auth.exception.LibrarianNotFoundException;
import maids.cc.librarymanagementsystem.book.exception.BookNotFoundException;
import maids.cc.librarymanagementsystem.borrowingrecord.exception.BookIsAlreadyBorrowedException;
import maids.cc.librarymanagementsystem.borrowingrecord.exception.BorrowedRecordDoesNotExist;
import maids.cc.librarymanagementsystem.patron.exception.PatronNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        //log.error("Exception caught :  {} ", ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler({BookNotFoundException.class, PatronNotFoundException.class, BorrowedRecordDoesNotExist.class, LibrarianNotFoundException.class})
    public ResponseEntity<String> notFoundException(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookIsAlreadyBorrowedException.class)
    public ResponseEntity<String> conflictException(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessage=result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.error("Exception caught :  {} ", errorMessage);

        return ResponseEntity.badRequest().body(errorMessage);
    }

}

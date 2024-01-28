package maids.cc.librarymanagementsystem.book.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;
import maids.cc.librarymanagementsystem.book.enums.Language;

import java.util.Date;

//its a class on purpose instead of a record to make it more readable and maintainable as for validation
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class BookRequest{
    @NotBlank(message = "isbn should not be null and must have at least one non-whitespace character")
    @Pattern(regexp = "\\d{10,13}", message = "isbn should allow only 10 to 13 digits") //ensures the isbn field matches the regular expression, which allows only 10 to 13 digits
    private final String isbn;

    @NotBlank(message = "title should not be null and must have at least one non-whitespace character")
    @Size(max = 100, message = "title's size should not be more than 100 characters")
    private final String title;

    @Size(min=3, message = "author's size should not be less than 3 characters")
    @Size(max=20, message = "author's size should not be more than 20 characters")
    private final String author;

    @PastOrPresent(message = "publication_date should be past or present")
    @NotNull(message = "publication_date should not be null")
    private final Date publicationDate;

    @NotNull(message = "language should not be null")
    private final Language language;

    @NotBlank(message = "description should not be null and must have at least one non-whitespace character")
    private final String description;
}

package maids.cc.librarymanagementsystem.patron.payload.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//its a class on purpose instead of a record to make it more readable and maintainable as for validation

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PatronRequest {
    @NotBlank(message="name should not be null and must have at least one non-whitespace character") //not null and must has one non-whitespace character
    @Size(min=2,message="name should be at least 2")
    @Size(max = 50,message="name should be at most 50")
    private final String name;

    @Email(message="email should be valid")
    private final String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be a valid international format")
    private final String phoneNumber;

}

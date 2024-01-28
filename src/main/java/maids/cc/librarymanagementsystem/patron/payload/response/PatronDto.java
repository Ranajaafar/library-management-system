package maids.cc.librarymanagementsystem.patron.payload.response;

import java.io.Serializable;

public record PatronDto (Long id,
                         String name,
                         String email,
                         String phoneNumber
) implements Serializable {
}